package com.learn.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * 发布主题生产者
 */
public class JmsProducer_topic_persisitent {

    private static final String BIND_URL = "tcp://192.168.1.105:61616";

    private static final String TOPIC_NAME = "topic-persistent";

    public static void main(String[] args) {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BIND_URL);

        try {
            Connection connection = activeMQConnectionFactory.createConnection();


            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            final Topic topic = session.createTopic(TOPIC_NAME);

            MessageProducer messageProducer = session.createProducer(topic);

            //设置生产持久化主题
            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
            //注：开始
            connection.start();


            for (int i = 1; i <= 3; i++) {
                final TextMessage textMessage = session.createTextMessage("zzh---" + i);
                messageProducer.send(textMessage);
            }


            messageProducer.close();
            session.close();
            connection.close();


        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
