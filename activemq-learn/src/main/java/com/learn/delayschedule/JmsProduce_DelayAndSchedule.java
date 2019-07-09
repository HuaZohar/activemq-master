package com.learn.delayschedule;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.ScheduledMessage;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsProduce_DelayAndSchedule {

    private static final String BROKER_URL = "tcp://192.168.1.105:61616";
    private static final String QUEUE_NAME = "queue-DelayAndSchedule-01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        activeMQConnectionFactory.setUseAsyncSend(true);
        final Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        final Queue queue = session.createQueue(QUEUE_NAME);

        final MessageProducer messageProducer = session.createProducer(queue);

        long delay = 3 * 1000;
        long period = 4 * 1000;
        int repeat = 5;

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= 3; i++) {
            final TextMessage textMessage = session.createTextMessage("queue delayandschedule msg --" + i);
            //延迟投递时间 3s
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
            //重复投递的时间间隔  4s
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
            //重复投递次数 5
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeat);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("-----消息发布到MQ完成");
    }
}
