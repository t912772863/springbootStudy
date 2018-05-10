package com.tian.common.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * 消息消费者.
 * 这个类只有一个. 但是却做了多个消息者的活. 如下, 每个方法上都可以通过指定@JmsListener注解, 来消费指定目的地的消息
 * 在原始的消费中, 每个消费者都是一个对象, 这里应该是通过拦截注解, 反射实现的. 好处在于把多个消费者统一管理.
 * Created by Administrator on 2018/5/10 0010.
 */
@Component
public class MessageConsumer {
    /** 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
     * */
    @JmsListener(destination = "sample.queue")
    public void receiveQueue(String text) {
        System.out.println("the message from queueat named 'sample.queue' is "+text);
    }

    @JmsListener(destination = "queue2")
    public void receiveQueue2(Message message) throws JMSException {
        System.out.println("the message from queue that named 'queue2' is "+((TextMessage)message).getText());
    }
}
