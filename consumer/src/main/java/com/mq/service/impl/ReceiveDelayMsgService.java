package com.mq.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author lizhengpeng
 * @create 2021/2/23 - 17:36
 * @describe 延迟队列接受信息
 */
@Service
@RabbitListener(queues = {"delay_queue2"})
public class ReceiveDelayMsgService {


    @RabbitHandler
    public void receiveMsg(String msg) {
        System.out.println( "接受延迟消息"+ msg);
    }


}
