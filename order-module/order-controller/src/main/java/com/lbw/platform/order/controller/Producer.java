package com.lbw.platform.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

@Service("producer")
public class Producer {
    @Autowired
    JmsMessagingTemplate jmsTemplate;

    /**
     * 发送消息
     *
     * @param destination 发送到的队列名
     * @param message     消息体
     */
    public void sendMessage(String destination, final String message) {
        jmsTemplate.convertAndSend(destination, message);
    }

    
}