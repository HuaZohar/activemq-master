package com.learn.boot.activemq.bootmqproduce.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

@Configuration
@EnableJms  // 重要开启JMS支持
public class JMSConfig {

    @Value("${myQueue}")
    private String myQueue;

    @Bean
    public Queue getQueue() {
        return new ActiveMQQueue(myQueue);
    }
}
