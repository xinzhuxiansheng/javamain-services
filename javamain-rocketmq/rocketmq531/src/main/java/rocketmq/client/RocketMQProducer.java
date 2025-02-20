package rocketmq.client;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

public class RocketMQProducer {
    public static void main(String[] args) {
        // 创建生产者实例，并指定生产者的组名
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");

        // 设置 NameServer 地址
        producer.setNamesrvAddr("192.168.0.130:9876");  // 替换为你的 RocketMQ NameServer 地址

        try {
            // 启动生产者
            producer.start();

            // 定义三个主题
            String[] topics = {"tp01", "tp02", "tp03"};

            // 定义消息内容
            String messageContent = "Hello RocketMQ";

            // 循环每隔 1 秒发送消息
            while (true) {
                // 遍历每个 topic
                for (String topic : topics) {
                    // 创建消息对象
                    Message message = new Message(topic, "TagA", (topic + " , " +  messageContent).getBytes());

                    // 发送消息
                    producer.send(message);

                    System.out.println("Message sent to " + topic + ": " + (topic + " , " +  messageContent));
                }

                // 每次循环间隔 1 秒
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 在程序结束时关闭生产者实例
            producer.shutdown();
        }
    }
}
