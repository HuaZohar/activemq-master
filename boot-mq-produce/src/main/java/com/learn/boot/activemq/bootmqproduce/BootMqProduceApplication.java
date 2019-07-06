package com.learn.boot.activemq.bootmqproduce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BootMqProduceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootMqProduceApplication.class, args);
    }

}
