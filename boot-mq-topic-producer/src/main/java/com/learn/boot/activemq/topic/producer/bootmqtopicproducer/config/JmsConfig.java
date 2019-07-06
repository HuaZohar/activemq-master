package com.learn.boot.activemq.topic.producer.bootmqtopicproducer.config;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Topic;

@Configuration
@EnableJms // 重要   开启JMS支持
public class JmsConfig {

    @Value("${myTopic}")
    private String topicName;

    @Bean
    public Topic getTopic(){
        return new ActiveMQTopic(topicName);
    }

}
