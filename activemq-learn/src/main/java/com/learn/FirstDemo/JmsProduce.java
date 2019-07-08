package com.learn.FirstDemo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsProduce {

//    private static final String BROKER_URL = "nio://192.168.1.105:61608";

    private static final String BROKER_URL = "tcp://192.168.1.105:61616";

    private static final String QUEUE_NAME = "queue-mysql-01";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

        final Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        final Queue queue = session.createQueue(QUEUE_NAME);

        final MessageProducer messageProducer = session.createProducer(queue);
        //持久化
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        for (int i = 1; i <= 3; i++) {
            final TextMessage textMessage = session.createTextMessage("queue jdbc msg --" + i);
            messageProducer.send(textMessage);
        }

        //8.关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("-----消息发布到MQ完成");
    }
}
