package com.mq.service.impl;

import com.mq.service.SendAckMsgService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lizhengpeng
 * @create 2021/2/24 - 10:15
 * @describe
 */
@Service
public class SendAckMsgServiceImpl implements SendAckMsgService {

    @Autowired
    private RabbitTemplate rabbitTemplate; //使用rabbitTemplate


    /**
     *
     * */
    public void sendAckMsg(String msg){
        if(msg.startsWith("a")){
            rabbitTemplate.convertAndSend( "DirectExchange","Direct-A",msg );
        }else if(msg.startsWith("b")){
            rabbitTemplate.convertAndSend( "DirectExchange","Direct-B",msg );
        }

    }


    /**
     * topic
     * */
    public void sendTopicMsg(String msg,String key){
        rabbitTemplate.convertAndSend( "topicExchange",key,msg );

    }

    /**
     * 测试接受消息报错
     * */
    public void sendErrorMsg(String msg){
        if(msg.startsWith("dev_a")){
            rabbitTemplate.convertAndSend( "","dev_queue",msg );
        }else if(msg.startsWith("dev_b")){
            rabbitTemplate.convertAndSend( "","dev1_queue",msg );
        }

    }

}
