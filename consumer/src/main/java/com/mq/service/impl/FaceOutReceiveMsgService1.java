package com.mq.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author lizhengpeng
 * @create 2021/2/23 - 10:15
 * @describe
 */
@Service
@RabbitListener(queues = {"queue3"})
public class FaceOutReceiveMsgService1 {
    //接受消息的传参存在两种，传参根据发送者形式，String和数组
    @RabbitHandler
    public void receiveMsg(String msg){
        System.out.println( "路由订阅模式queue3接受消息"+ msg);

    }
}
