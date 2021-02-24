package com.mq.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mq.dto.Goods;
import com.mq.service.SendMsgService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhengpeng
 * @create 2021/2/23 - 9:45
 * @describe
 */

@Service
public class SendMsgServiceImpl implements SendMsgService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate; //使用rabbitTemplate


    public void sendMsg(String msg){
        if(msg.startsWith( "q_" )){
            //简单模式和工作模式，发送队列
            amqpTemplate.convertAndSend( "queue1" ,msg);
        }else if(msg.startsWith( "f_" )){
            //交换机订阅模式(fanout)
            amqpTemplate.convertAndSend( "ex1" ,"",msg);
        }else if(msg.startsWith( "r_a")){
            //交换机模式(direct)，第二个参数给参数就代表是key
            amqpTemplate.convertAndSend( "ex2" ,"a",msg);
        }else if(msg.startsWith( "r_b")){
            //交换机模式(direct)，第二个参数给参数就代表是key
            amqpTemplate.convertAndSend( "ex2" ,"b",msg);
        }else if(msg.startsWith( "r_c")){
            //交换机模式(direct)，测试确认机制和回退机制
            amqpTemplate.convertAndSend( "ex2" ,"c",msg);
        }
    }


    /*
     *
     * 发送商品对象
     * */
    public void sendAddGoods(Goods goods){
        //使用对象传输
        //amqpTemplate.convertAndSend( "queue2",goods);
        rabbitTemplate.convertAndSend( "","queue2",goods );

    }

    /*
     *
     * 发送商品对象 数组传输
     * */
    public void sendAddGoodsAsByte(Goods goods){
        //使用数组传输
        byte[] serialize = SerializationUtils.serialize(goods);
        //amqpTemplate.convertAndSend( "queue2",serialize);
        rabbitTemplate.convertAndSend( "","queue2",goods );
    }

    /*
     *
     * 发送商品对象，json字符串
     * */
    public void sendAddGoodsAsJson(Goods goods){
        //使用json字符串
        ObjectMapper objectMapper=new ObjectMapper();
        String json=null;
        try {
            json=objectMapper.writeValueAsString(goods);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //amqpTemplate.convertAndSend( "queue2",json);
        rabbitTemplate.convertAndSend( "","queue2",goods );

    }

    /*
     *
     * 发送商品对象，json字符串
     * */
    public void sendAddGoodsAsJsonList(){
        Goods goods1=new Goods();
        goods1.setGoodsId( "1" );
        goods1.setGoodsName("手机");
        Goods goods2=new Goods();
        goods2.setGoodsId( "2" );
        goods2.setGoodsName("电脑");
        List<Goods> list=new ArrayList<>();
        list.add( goods1 );
        list.add( goods2 );
        //使用json字符串
        ObjectMapper objectMapper=new ObjectMapper();
        String json=null;
        try {
            json=objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        amqpTemplate.convertAndSend( "queue2",json);
        //rabbitTemplate.convertAndSend( "","queue2",json );
    }

    /**
     *
     * 延迟发送 发送到死信队列
     * */
    public void sendDelayMsg(String msg){
        rabbitTemplate.convertAndSend( "delay_exchange","k1",msg );
    }
}
