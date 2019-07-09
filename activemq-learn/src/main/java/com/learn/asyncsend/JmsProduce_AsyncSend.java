package com.learn.asyncsend;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.UUID;

public class JmsProduce_AsyncSend {

    private static final String BROKER_URL = "tcp://192.168.1.105:61616";
    private static final String QUEUE_NAME = "queue-async-01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        //开启异步
        activeMQConnectionFactory.setUseAsyncSend(true);
        final Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        final Queue queue = session.createQueue(QUEUE_NAME);

        final ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer) session.createProducer(queue);

        for (int i = 1; i <= 3; i++) {
            final TextMessage textMessage = session.createTextMessage("queue jdbc msg --" + i);
            textMessage.setJMSMessageID(UUID.randomUUID().toString() + "-jms-produce-order-01");

            final String messageID = textMessage.getJMSMessageID();

            //异步保证发送成功，回调打印异常数据
            activeMQMessageProducer.send(textMessage, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println(messageID + " has been ok");
                }

                @Override
                public void onException(JMSException e) {
                    System.out.println(messageID + " fail");
                }
            });
        }

        activeMQMessageProducer.close();
        session.close();
        connection.close();

        System.out.println("-----消息发布到MQ完成");
    }
}
