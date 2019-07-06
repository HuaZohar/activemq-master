package com.learn.activemq.broker;

import org.apache.activemq.broker.BrokerService;

/**
 * 自建消息服务器
 */
public class EmbeBroker {

    public static void main(String[] args) {

        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        try {
            //设置服务地址
            brokerService.addConnector("tcp://localhost:61616");
            //启动
            brokerService.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
