package rocketmq.client;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class RocketMQConsumer {
    public static void main(String[] args) {
        // 创建消费者实例，并指定消费组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName");

        // 设置 NameServer 地址
        consumer.setNamesrvAddr("192.168.0.130:9876");  // 替换为你的 RocketMQ NameServer 地址

        try {
            // 订阅多个主题
            consumer.subscribe("tp01", "*"); // 订阅 tp01 主题
            consumer.subscribe("tp02", "*"); // 订阅 tp02 主题
            consumer.subscribe("tp03", "*"); // 订阅 tp03 主题

            // 设置消息监听器（并发消费）
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (MessageExt msg : msgs) {
                        // 处理消息
                        System.out.println("Consumer received message: " + new String(msg.getBody()));
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            // 启动消费者实例
            consumer.start();

            System.out.println("Consumer started. Listening on tp01, tp02, tp03...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
