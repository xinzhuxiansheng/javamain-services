package com.javamain.zookeeperFeatures.queue;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.recipes.queue.QueueSerializer;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author yzhou
 * @date 2022/5/31
 */
public class DistributedQueueExample {
    private static final Logger logger = LoggerFactory.getLogger(DistributedQueueExample.class);
    private static final String PATH = "/example/customize-queue";
    private static final String CONNECT_IP_PORT = "test.zk.com:2181";

    public static void main(String[] args) throws Exception {
        CuratorFramework client = null;
        DistributedQueue<String> queue = null;
        try {
            client = CuratorFrameworkFactory.builder().connectString(CONNECT_IP_PORT).sessionTimeoutMs(5000)
                    .connectionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
            client.start();
            logger.debug("创建连接成功：{}", client);

            // 消费者
            QueueConsumer<String> consumer = createQueueConsumer();
            // 生产者
            queue = QueueBuilder.builder(client, consumer, createQueueSerializer(), PATH).buildQueue();
            queue.start();

            for (int i = 0; i < 10; i++) {
                queue.put(" test-" + i);
            }

            TimeUnit.SECONDS.sleep(200);

        } catch (Exception ex) {
        } finally {
            CloseableUtils.closeQuietly(queue);
            CloseableUtils.closeQuietly(client);
        }
    }

    private static QueueSerializer<String> createQueueSerializer() {
        return new QueueSerializer<String>() {
            @Override
            public byte[] serialize(String item) {
                return item.getBytes();
            }

            @Override
            public String deserialize(byte[] bytes) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return new String(bytes);
            }

        };
    }

    private static QueueConsumer<String> createQueueConsumer() {
        return new QueueConsumer<String>() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                logger.debug("状态改变，当前状态：{}", newState.name());
            }

            @Override
            public void consumeMessage(String message) throws Exception {
                logger.debug("消费者消费消息：{}", message);
            }
        };
    }
}
