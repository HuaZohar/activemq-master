package com.learn.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * 1.先生产，只启动1号消费者，问题：1号消费者能消费消息吗？
 * Y
 *
 * 2.先生产，先启动1号消费者再启动2号消费者，问题：2号消费者还能消费消息吗？
 *  2号消费者不能消耗消息
 *
 * 3.先启动2个消费者，再生产6条消息，问题：消费情况
 *
 * 一个消费者一半（轮询消费）
 *
 */
public class JmsConsumer {

//    private static final String BROKER_URL = "tcp://192.168.1.105:61616";

//    private static final String BROKER_URL = "nio://192.168.1.105:61618";

    private static final String BROKER_URL = "nio://192.168.1.105:61608";

    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {

        //1.创建工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

        //2.创建连接
        final Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地
        final Queue queue = session.createQueue(QUEUE_NAME);

        //5.创建消费者
        final MessageConsumer messageConsumer = session.createConsumer(queue);

        /**
         * 同步阻塞方式
         *订阅者或者接受者调用messageConsumer的receive()方法来接收消息，receive方法在能够接收到消息之前（或者超时之前）将一直阻塞
         *
         while (true) {
         //6.读取消息  可以设置超时
         final TextMessage textMessage = (TextMessage) messageConsumer.receive(4000L);
         if (textMessage == null) {
         break;
         }
         System.out.println(textMessage.getText());
         }
         //7.关闭资源
         messageConsumer.close();
         session.close();
         connection.close();
         */

        /**
         * 通过监听的方式消费消息
         */
        //获取消息监听者
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (null != message && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println(textMessage.getText());

                        System.out.println("消息属性："+textMessage.getStringProperty("name"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
/*  map消息测试
              if (null != message && message instanceof MapMessage) {
                    MapMessage mapMessage = (MapMessage) message;
                    try {
                        System.out.println(mapMessage.getString("k1"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }*/
            }
        });

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        messageConsumer.close();
        session.close();
        connection.close();


        System.out.println("-----消息消费完成");
    }
}
