package com.javamain.rocketmq.example01;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class SimpleProducer {
    public static void main(String[] args) throws Exception {
        // 1. 创建生产者并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("MyProducerGroup");

        // 2. 设置 NameServer 地址
        producer.setNamesrvAddr("192.168.0.201:9876");  // RocketMQ NameServer 地址（可以是多个，用 ; 分隔）

        // 3. 启动生产者实例
        producer.start();

        int count = 0;

        // 4. 使用 while(true) 循环定时发送消息
        while (true) {
            try {
                // 5. 创建消息对象
                String topic = "yzhoutpjson01";
                String tag = "TagA";
                String body = "Hello RocketMQ " + count++;
                Message msg = new Message(topic, tag, body.getBytes());

                // 6. 发送消息（同步发送）
                SendResult sendResult = producer.send(msg);
                //System.out.println("Sent message: " + body + " | SendResult: " + sendResult);

                // 7. 每隔 3 秒发送一条消息
                //Thread.sleep(3000);  // 3 秒钟间隔
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // producer.shutdown();  // 这行代码永远不会执行，因为 while(true) 会一直运行
    }
}
