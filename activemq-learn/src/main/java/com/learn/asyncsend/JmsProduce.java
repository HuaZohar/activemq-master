package com.learn.asyncsend;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsProduce {

    private static final String BROKER_URL = "tcp://192.168.1.105:61616";
    private static final String QUEUE_NAME = "queue-mysql-01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        activeMQConnectionFactory.setUseAsyncSend(true);
        final Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        final Queue queue = session.createQueue(QUEUE_NAME);

        final ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer) session.createProducer(queue);

        for (int i = 1; i <= 3; i++) {
            final TextMessage textMessage = session.createTextMessage("queue jdbc msg --" + i);
            activeMQMessageProducer.send(textMessage, new AsyncCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onException(JMSException e) {

                }
            });
        }

        activeMQMessageProducer.close();
        session.close();
        connection.close();

        System.out.println("-----消息发布到MQ完成");
    }
}
