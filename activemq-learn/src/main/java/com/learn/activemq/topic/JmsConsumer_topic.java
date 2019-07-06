package com.learn.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import java.io.IOException;

/**
 * 订阅主题的消费者
 */
public class JmsConsumer_topic {

    private static final String BIND_URL = "tcp://192.168.43.177:61616";

    private static final String TOPIC_NAME = "topic-1";

    public static void main(String[] args) {
        //1.创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BIND_URL);
        Connection connection = null;
        Session session = null;
        MessageConsumer messageConsumer = null;
        try {
            //创建连接
            connection = activeMQConnectionFactory.createConnection();
            //开启连接
            connection.start();
            //创建会话
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            final Topic topic = session.createTopic(TOPIC_NAME);

            messageConsumer = session.createConsumer(topic);

            messageConsumer.setMessageListener((message) -> {
                if (null != message && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("topic-1:" + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

            System.in.read();

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                messageConsumer.close();
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

        System.out.println("topic-1消费完成");


    }

}
