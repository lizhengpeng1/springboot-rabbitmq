package com.mq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author lizhengpeng
 * @create 2021/2/23 - 17:19
 * @describe
 */
@Component
public class PublisherConfireAndReturnConfig implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {

    Logger logger = LoggerFactory.getLogger(PublisherConfireAndReturnConfig.class);

    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initMethod(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if(b){
            logger.info("--------消息发送(到交换机)成功");
        }else{
            logger.warn("--------消息发送(到交换机)失败");
        }

    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        logger.info("~~~~~~~~消息发送到交换机但未分发到队列！！！");

    }
}
