package com.learn.boot.activemq.bootmqproduce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.UUID;

@Component
public class Queue_Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue;

    public void produceMsg() {
        jmsMessagingTemplate.convertAndSend(queue, "***" + UUID.randomUUID().toString().substring(3));
        System.out.println("produce msg ...");
    }

    /**
     * 开启定时注解  3秒执行一次
     */
    @Scheduled(fixedDelay = 3000)
    public void produceMsgScheduled(){
        produceMsg();
    }
}
