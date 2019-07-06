package com.learn.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsProduce_tx {

    private static final String BROKER_URL = "tcp://192.168.1.105:61616";

    private static final String QUEUE_NAME = "queue_tx";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        final Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        /**
         * 创建会话
         * 第一个参数 是否开启事务
         * 第二个参数 签收  自动签收
         */
        final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        final Queue queue = session.createQueue(QUEUE_NAME);
        final MessageProducer producer = session.createProducer(queue);
        for (int i = 1; i <= 3; i++) {
            final TextMessage textMessage = session.createTextMessage("MessageListener--" + i);
            producer.send(textMessage);
        }

        /**
         * 开启事务之后必须开启提交
         */
        session.commit();

        producer.close();
        session.close();
        connection.close();

        System.out.println("-----消息发布到MQ完成");
    }
}
