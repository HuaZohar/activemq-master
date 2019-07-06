package com.learn.boot.activemq.bootmqproduce;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BootMqProduceApplicationTests {

    @Resource
    private Queue_Producer queue_producer;

    @Test
    public void contextLoads() {
        queue_producer.produceMsg();
    }

}
