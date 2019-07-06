package com.learn.boot.activemq.bootmqconsumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Service
public class Queue_Consumer {

    @JmsListener(destination = "${myQueue}")
    private void receive(TextMessage textMessage) throws JMSException {
        System.out.println("********消费者接收到消息：" + textMessage.getText());
    }

}
