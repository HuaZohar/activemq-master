package com.learn.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

/**
 * 订阅主题的消费者
 */
public class JmsConsumer_topic_persistent {

    private static final String BIND_URL = "tcp://192.168.1.105:61616";

    private static final String TOPIC_NAME = "topic-persistent";

    public static void main(String[] args) {

        System.out.println("z3");

        //1.创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BIND_URL);
        Connection connection = null;
        Session session = null;
        try {
            //创建连接
            connection = activeMQConnectionFactory.createConnection();
            connection.setClientID("z3");
            //创建会话
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            final Topic topic = session.createTopic(TOPIC_NAME);

            final TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark...");

            connection.start();


            Message message = topicSubscriber.receive();
            while (message != null) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("****收到持久化主题消息：" + textMessage.getText());

                message = topicSubscriber.receive(5000);
            }


        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

        System.out.println("topic-1消费完成");


    }

}
