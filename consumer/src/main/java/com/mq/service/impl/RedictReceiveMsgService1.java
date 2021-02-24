package com.mq.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author lizhengpeng
 * @create 2021/2/23 - 10:20
 * @describe
 */
@Service
@RabbitListener(queues = {"queue5"})
public class RedictReceiveMsgService1 {
    //接受消息的传参存在两种，传参根据发送者形式，String和数组
    @RabbitHandler
    public void receiveMsg(String msg){
        System.out.println( "交换机路由模式queue5接受消息"+ msg);

    }
}
