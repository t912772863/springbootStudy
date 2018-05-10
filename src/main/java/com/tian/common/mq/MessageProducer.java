package com.tian.common.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * 消息生产者对象.
 * 一个就可以了, 生产出消息后, 放入到不同的目的地
 * Created by Administrator on 2018/5/10 0010.
 */
@Component
public class MessageProducer {
    @Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    private JmsMessagingTemplate jmsTemplate;
    // 发送消息，destination是发送到的队列，message是待发送的消息
    public void sendMessage(Destination destination, final String message){
        jmsTemplate.convertAndSend(destination, message);
    }
}
