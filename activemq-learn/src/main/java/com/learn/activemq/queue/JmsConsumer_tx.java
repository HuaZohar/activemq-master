package com.learn.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 消费者 签收
 */
public class JmsConsumer_tx {


    private static final String BROKER_URL = "tcp://192.168.1.105:61616";

    private static final String QUEUE_NAME = "queue_tx";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        final Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        /**
         * 第一参数：是否开启事务
         * 第二参数：手动签收
         */
        final Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);

        final Queue queue = session.createQueue(QUEUE_NAME);

        final MessageConsumer messageConsumer = session.createConsumer(queue);

        while (true) {
            final TextMessage textMessage = (TextMessage) messageConsumer.receive(4000L);
            if (textMessage == null) {
                break;
            }
            System.out.println(textMessage.getText());
            //消费者客户端调用acknowledge()进行手动回收  手动回收
            textMessage.acknowledge();
        }
        //提交
        session.commit();

        messageConsumer.close();
        session.close();
        connection.close();


        System.out.println("-----消息消费完成");
    }
}
