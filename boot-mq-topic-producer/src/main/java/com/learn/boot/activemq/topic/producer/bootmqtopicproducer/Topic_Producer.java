package com.learn.boot.activemq.topic.producer.bootmqtopicproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.Topic;
import java.util.UUID;

@Service
public class Topic_Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Topic topic;

    public void produceTopic() {
        jmsMessagingTemplate.convertAndSend(topic, "topic msg is : " + UUID.randomUUID().toString().substring(0, 6));
        System.out.println("produce topic schedule task ...");
    }

    /**
     * 开启定时任务 3秒执行一次
     */
    @Scheduled(fixedDelay = 3000)
    public void produceTopicSchedule(){
        produceTopic();
    }

}
