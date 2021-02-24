package com.mq.service.ack;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;


/**
 * @author lizhengpeng
 * @create 2021/2/24 - 9:42
 * @describe
 */
@Component
public class AckReceiveMsgService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(bindings = @QueueBinding(//绑定队列和交换机
            value = @Queue(value = "Direct-A"),//创建queuqe
            exchange = @Exchange(value = "DirectExchange",type = "direct"),//创建交换机
            key = "Direct-A"//路由规则，routingkey如果是Direct-A就发到这个监听

    ))
    public void directReceiverA(String msg, Channel channel, Message message){
        //channel.basicAck() 表示消息正常消费
        //channel.basicNack() 消息消费异常，将消息重新往往队列
        //channel.basicReject() 消息消费异常，拒绝该消息入队列

        //消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        //ack返回false，并重新回到队列，api里面解释得很清楚
        //第一个参数依然是当前消息到的数据的唯一id;
        //第二个参数是指是否针对多条消息；如果是true，也就是说一次性针对当前通道的消息的tagID小于当前这条消息的，都拒绝确认。
        //第三个参数是指是否重新入列，也就是指不确认的消息是否重新丢回到队列里面去
        //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        //拒绝消息
        //拒绝消费当前消息，如果第二参数传入true，就是将数据重新丢回队列里，那么下次还会消费这消息。
        // 设置false，就是告诉服务器，我已经知道这条消息数据了，因为一些原因拒绝它，而且服务器也把这个消息丢掉就行。 下次不想再消费这条消息了
        //channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        try {
            //正常消费消息
            System.out.println("directReceiverA正常消费消息："+msg);
            //应答消息队列
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // redelivered = true, 表明该消息是重复处理消息
            Boolean redelivered = message.getMessageProperties().getRedelivered();
            /**
             * 这里对消息重入队列做设置，例如将消息序列化缓存至 Redis, 并记录重入队列次数
             * 如果该消息重入队列次数达到一次次数，比如3次，将不再重入队列，直接拒绝
             * 这时候需要对消息做补偿机制处理
             *
             * channel.basicNack与channel.basicReject要结合越来使用
             *
             */
            try {
                if (redelivered){
                    /**
                     * 1. 对于重复处理的队列消息做补偿机制处理
                     * 2. 从队列中移除该消息，防止队列阻塞
                     */
                    // 消息已重复处理失败, 扔掉消息
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); //拒绝消息
                    System.out.println("消息重新处理失败，扔掉消息"+ msg);
                }

                // redelivered != true,表明该消息是第一次消费
                if (!redelivered) {
                    // 消息重新放回队列
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                    System.out.println("消息处理失败，重新放回队列"+ msg);
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        System.out.println("directReceiverA:"+message.getBody());
        System.out.println("directReceiverA:"+message.toString() );
        System.out.println("directReceiverA:"+message.getMessageProperties().getAppId() );
        System.out.println("directReceiverA:"+message.getMessageProperties().getMessageId() );
        System.out.println("directReceiverA:"+message.getMessageProperties().getReceivedExchange() );
        System.out.println("directReceiverA:"+message.getMessageProperties().getReceivedRoutingKey() );
        System.out.println("directReceiverA:"+message.getMessageProperties().getDeliveryTag() );
        System.out.println("directReceiverA:"+message.getMessageProperties().getHeaders() );

    }

    @RabbitListener(bindings =@QueueBinding(//绑定队列和交换机
            value = @Queue(value = "Direct-B"),//创建queuqe
            exchange = @Exchange(value="DirectExchange",type = "direct"),//创建交换机
            key="Direct-B"
            ))
    public  void directReceiverB(String msg,Message message, Channel channel){
        //channel.basicAck() 表示消息正常消费
        //channel.basicNack() 消息消费异常，将消息重新往往队列
        //channel.basicReject() 消息消费异常，拒绝该消息入队列
        try {
            //正常消费消息
            System.out.println("directReceiverA正常消费消息："+msg);
            //应答消息队列
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // redelivered = true, 表明该消息是重复处理消息
            Boolean redelivered = message.getMessageProperties().getRedelivered();
            /**
             * 这里对消息重入队列做设置，例如将消息序列化缓存至 Redis, 并记录重入队列次数
             * 如果该消息重入队列次数达到一次次数，比如3次，将不再重入队列，直接拒绝
             * 这时候需要对消息做补偿机制处理
             *
             * channel.basicNack与channel.basicReject要结合越来使用
             *
             */
            try {
                if (redelivered){
                    /**
                     * 1. 对于重复处理的队列消息做补偿机制处理
                     * 2. 从队列中移除该消息，防止队列阻塞
                     */
                    // 消息已重复处理失败, 扔掉消息
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); //拒绝消息
                    System.out.println("消息重新处理失败，扔掉消息"+ msg);
                }

                // redelivered != true,表明该消息是第一次消费
                if (!redelivered) {
                    // 消息重新放回队列
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                    System.out.println("消息处理失败，重新放回队列"+ msg);
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        System.out.println("directReceiverB:"+message.getBody());
        System.out.println("directReceiverB:"+message.toString() );
        System.out.println("directReceiverB:"+message.getMessageProperties().getAppId() );
        System.out.println("directReceiverB:"+message.getMessageProperties().getMessageId() );
        System.out.println("directReceiverB:"+message.getMessageProperties().getReceivedExchange() );
        System.out.println("directReceiverB:"+message.getMessageProperties().getReceivedRoutingKey() );
        System.out.println("directReceiverB:"+message.getMessageProperties().getDeliveryTag() );
        System.out.println("directReceiverB:"+message.getMessageProperties().getHeaders() );

    }

    @RabbitListener(bindings = {@QueueBinding(value=@Queue("topic01"),key = {"aa"},exchange =@Exchange(name = "topicExchange",type = "topic"))})
    public void  topicReceive01(String msg,Message message,Channel channel) throws IOException {
        channel.basicAck( message.getMessageProperties().getDeliveryTag(),false);
        // 消息重新放回队列
        //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true  );
        //消息消费异常，拒绝该消息入队列
        //channel.basicReject( message.getMessageProperties().getDeliveryTag(),false );

        System.out.println("topic01消费者---"+msg );
    }
    @RabbitListener(bindings = {@QueueBinding(value=@Queue("topic02"),key = {"aa.*"},exchange =@Exchange(name = "topicExchange",type = "topic"))})
    public void  topicReceive02(String msg,Message message,Channel channel) throws IOException {

        channel.basicAck( message.getMessageProperties().getDeliveryTag(),false );
        System.out.println("topic02消费者 ----"+msg );
    }
    @RabbitListener(bindings = {
                    @QueueBinding(value=@Queue("topic03"),
                     key = {"aa.#"}, exchange =@Exchange(name = "topicExchange",type = "topic"))})
    public void  topicReceive03(String msg,Message message,Channel channel) throws IOException {
        channel.basicAck( message.getMessageProperties().getDeliveryTag(),false );
        System.out.println("topic03消费者 ---"+msg );
    }

    /*
    * 测试处理异常
    * */
    @RabbitListener(queuesToDeclare = @Queue("dev_queue"))
    public void getMsg(String msg, Channel channel, Message message){
        try {
            System.out.println( "*****dev_queue接受消息****msg:"+msg);
            int i=10/0;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false );
        } catch (Exception e) {
            //消息是否重复处理消息
            Boolean redelivered = message.getMessageProperties().getRedelivered();
            System.out.println( "*****dev_queue接受消息,是否重新发送处理了redelivered:"+redelivered);
            try{
                //判断是否已经重新尝试发送请求了
                if(redelivered){
                    //补偿机制，发送到其他队列（死信队列），记录日志
                    rabbitTemplate.convertAndSend( "","queue_sx", msg);
                    //拒绝消息,不在重新入队列
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                    System.out.println( "*****dev_queue***basicReject,消息重新处理失败，扔掉消息Msg:"+msg);
                }else{
                    //重新回到队列
                    System.out.println( "*****dev_queue***basicReject 重新回到队列Msg:"+msg);
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
