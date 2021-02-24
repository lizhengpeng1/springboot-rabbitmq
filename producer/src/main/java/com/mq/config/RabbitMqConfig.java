package com.mq.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * @author lizhengpeng
 * @create 2021/2/23 - 14:24
 * @describe
 */
@Configuration
public class RabbitMqConfig {

    /*
    *  创建队列
    * */
    @Bean
    public Queue queue9(){
        return new Queue("queue9");
    }

    //创建队列
    @Bean
    public Queue queue10(){
        Queue queue10=new Queue("queue10");
        //这里可以设置队列属性
        return queue10;
    }

    //创建订阅交换机
    @Bean
    public Exchange ex3(){
        return new FanoutExchange("ex3");
    }

    //创建路由交换机
    @Bean
    public Exchange ex4(){
        return new DirectExchange("ex4");
    }

    //创建Topic交换机
    @Bean
    public Exchange ex5(){
        return new TopicExchange("ex5");
    }


    //队列绑定交换机
    @Bean
    public BindingBuilder.GenericArgumentsConfigurer BindgQueueToExchange(Queue queue9, Exchange ex3){
        return BindingBuilder.bind(queue9).to(ex3).with("rk1");
    }




}

