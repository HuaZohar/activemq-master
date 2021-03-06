package com.learn.delayschedule;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * 消费者
 */
public class JmsConsumer_DelayAndSchedule {

    private static final String BROKER_URL = "tcp://192.168.1.105:61616";
    private static final String QUEUE_NAME = "queue-DelayAndSchedule-01";

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        final Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        final Queue queue = session.createQueue(QUEUE_NAME);

        final MessageConsumer messageConsumer = session.createConsumer(queue);
        while (true) {
            final TextMessage textMessage = (TextMessage) messageConsumer.receive(4000L);
            if (textMessage == null) {
                break;
            }
            System.out.println(System.currentTimeMillis() + "-" + textMessage.getText());
        }

        System.in.read();



        messageConsumer.close();
        session.close();
        connection.close();
        System.out.println("-----消息消费完成");
    }
}
