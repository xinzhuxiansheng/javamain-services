package com.javamain.rocketmq.example01;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

public class SimpleConsumer {
    public static void main(String[] args) throws Exception {
        // 1. 创建消费者实例，并指定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("MyConsumerGroup");

        // 2. 设置 NameServer 地址
        consumer.setNamesrvAddr("192.168.0.201:9876");  // RocketMQ NameServer 地址（可以是多个，用 ; 分隔）

        // 3. 订阅主题和标签
        consumer.subscribe("yzhoutpjson01", "TagA");  // 订阅 "mytopic" 主题和 "mytag" 标签的消息

        // 4. 设置消息监听器（并发消费）
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                // 5. 消费消息
                System.out.println("Received message: " + new String(msg.getBody()));
            }
            return null;  // 返回消费成功
        });

        // 6. 启动消费者
        consumer.start();

        System.out.println("RocketMQ Consumer started.");
    }
}
