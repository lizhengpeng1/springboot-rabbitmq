package com.mq.controller;

import com.mq.dto.Goods;
import com.mq.service.SendAckMsgService;
import com.mq.service.SendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * @author lizhengpeng
 * @create 2021/2/23 - 9:41
 * @describe
 */
@RestController
public class SendMsgController {

    @Autowired
    private SendMsgService sendMsgService;

    @Autowired
    private SendAckMsgService sendAckMsgService;


    @RequestMapping("/sendMsg")
    public String sendMsg(String msg){
        sendMsgService.sendMsg(msg);
        return "ok";
    }

    @RequestMapping(value = "/addGoods")
    public String addGoods(@RequestParam Map<String,String> map){
        Goods goods=new Goods();
        goods.setGoodsId(map.get("goodsId"));
        goods.setGoodsName(map.get("goodsName"));
        if("1".equals( map.get("sendType"))){
            //对象形式发送
            sendMsgService.sendAddGoods(goods);
        }else if("2".equals( map.get("sendType"))){
            //数组形式发送
            sendMsgService.sendAddGoodsAsByte(goods);
        }else if("3".equals( map.get("sendType"))){
            //json字符串形式发送
            sendMsgService.sendAddGoodsAsJson(goods);
        }
        return "ok";
    }

    @RequestMapping(value = "/sendGoods")
    public String sendGoods(){
        //json字符串形式发送
        sendMsgService.sendAddGoodsAsJsonList();
        return "ok";
    }

    /**
     *
     * 延迟发送 发送到死信队列
     * */
    @RequestMapping("/sendDelayMsg")
    public String sendDelayMsg(String msg){
        sendMsgService.sendDelayMsg(msg);
        return "ok";
    }

    @RequestMapping("/sendAckMsg")
    public String sendAckMsg(String msg){
        sendAckMsgService.sendAckMsg(msg);
        return "ok";
    }

    @RequestMapping("/sendTopicMsg")
    public String sendTopicMsg(@RequestParam("msg") String msg,@RequestParam("key") String key){
        sendAckMsgService.sendTopicMsg(msg,key);
        return "ok";
    }

    /**
     * 测试接受消息报错
     * */
    @RequestMapping("/sendErrorMsg")
    public String sendErrorMsg(String msg){
        sendAckMsgService.sendErrorMsg(msg);
        return "ok";
    }



}
