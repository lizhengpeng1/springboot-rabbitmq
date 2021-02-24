package com.mq.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author lizhengpeng
 * @create 2021/2/24 - 13:14
 * @describe
 */
@Component
public class ReceiveMsgService{

    @RabbitListener(queuesToDeclare = @Queue("dev1_queue"))
    public void getMsg(String msg, Channel channel, Message message){
        System.out.println( "dev_queue接受消息");
        try {
            int i=10/0;
        }catch (Exception e){

        }

    }

}
