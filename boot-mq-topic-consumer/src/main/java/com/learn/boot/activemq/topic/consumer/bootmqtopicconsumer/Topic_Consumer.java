package com.learn.boot.activemq.topic.consumer.bootmqtopicconsumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class Topic_Consumer {

    @JmsListener(destination = "${myTopic}")
    public void receive(TextMessage textMessage) throws JMSException {
        final String text = textMessage.getText();
        System.out.println("消费者收到订阅的主题：" + text);
    }
}
