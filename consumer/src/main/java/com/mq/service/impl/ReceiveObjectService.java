package com.mq.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mq.dto.Goods;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhengpeng
 * @create 2021/2/23 - 10:45
 * @describe
 */
@Service
@RabbitListener(queues = {"queue2"})
public class ReceiveObjectService {

    /*接受消息的传参
     * 对象需要序列号，且对象的序列化信息一致（发送者和消费者对象的包名，属性名一致）
     *
     */
    @RabbitHandler
    public void receiveMsg(Goods goods){
        System.out.println( "接受对象消息"+ goods);
    }

    //发送者和消费者对象的属性名一致）
    @RabbitHandler
    public void receiveMsg(String msg) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        Goods goods = objectMapper.readValue(msg, Goods.class );
        //List<Goods> goods= objectMapper.readValue(msg, List.class );
        System.out.println( "接受Json对象消息"+ goods);
    }

    //数组接受
    @RabbitHandler
    public void receiveMsg(byte[] msg){
        Goods goods =(Goods)SerializationUtils.deserialize( msg );
        System.out.println( "接受byte数组对象消息"+ goods);
    }



}
