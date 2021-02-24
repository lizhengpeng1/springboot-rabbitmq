package com.mq.service;

import com.mq.dto.Goods;
import org.springframework.stereotype.Service;

/**
 * @author lizhengpeng
 * @create 2021/2/23 - 9:41
 * @describe
 */
public interface SendMsgService {

    public void sendMsg(String msg);

    /*
     *
     * 发送商品对象
     * */
    public void sendAddGoods(Goods goods);

    /*
     *
     * 发送商品对象 数组传输
     * */
    public void sendAddGoodsAsByte(Goods goods);

    /*
     *
     * 发送商品对象，json字符串
     * */
    public void sendAddGoodsAsJson(Goods goods);

    /*
     *
     * 发送商品对象，json字符串
     * */
    public void sendAddGoodsAsJsonList();

    /**
     *
     * 延迟发送 发送到死信队列
     * */
    public void sendDelayMsg(String msg);
}
