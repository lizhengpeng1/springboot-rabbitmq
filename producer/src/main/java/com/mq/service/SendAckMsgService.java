package com.mq.service;

/**
 * @author lizhengpeng
 * @create 2021/2/24 - 10:15
 * @describe
 */
public interface SendAckMsgService {

    public void sendAckMsg(String msg);

    /**
     * topic
     * */
    public void sendTopicMsg(String msg,String key);

    /**
     * 测试接受消息报错
     * */
    public void sendErrorMsg(String msg);
}
