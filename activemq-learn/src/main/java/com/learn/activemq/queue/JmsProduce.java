package com.learn.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsProduce {

    private static final String BROKER_URL = "tcp://192.168.1.105:61616";

    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {

        //1.创建工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

        //2.创建连接
        final Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地（具体是队列还是主题？）
        final Queue queue = session.createQueue(QUEUE_NAME);

        //5.创建消息的生产者
        final MessageProducer producer = session.createProducer(queue);

        //通过使用消息生产者producer生产3条消息发送到MQ队列里面
        for (int i = 1; i <= 3; i++) {

            //6.创建消息
            final TextMessage textMessage = session.createTextMessage("MessageListener--" + i);

            //设置属性
            textMessage.setStringProperty("name","zz33");
            //7.通过消息生产者producer发布给mq
            producer.send(textMessage);



/*            map消息测试
            final MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("k1","mapMessage----v1");
            producer.send(mapMessage);*/
        }
        //8.关闭资源
        producer.close();
        session.close();
        connection.close();

        System.out.println("-----消息发布到MQ完成");
    }
}
