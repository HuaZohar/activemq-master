package com.learn.spring.activemq.queueandtopic;


import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;

/**
 * 生产者
 */
@Service
public class JmsProduce {

    @Autowired
    private JmsTemplate jmsTemplate;


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        final JmsProduce jmsProduce = (JmsProduce) applicationContext.getBean("jmsProduce");
        //发送消息
        jmsProduce.jmsTemplate.send(session -> {
            final TextMessage textMessage = session.createTextMessage("spring-producer-activemq-queueandtopic-msg....123");
            return textMessage;
        });

        System.out.println("spring-producer-send-msg-over.");
    }
}
