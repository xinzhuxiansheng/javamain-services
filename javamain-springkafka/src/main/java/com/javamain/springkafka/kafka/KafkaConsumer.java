package com.javamain.springkafka.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(
            id = "cdclistener",
            topics = {"yzhoutpjson01"},
            groupId = "gid012401"
    )
    public void consumer(String record, Acknowledgment ack) {
        String threadName = Thread.currentThread().getName();
        try {
            logger.info("线程: {} 接收到消息: {}", threadName, record);
        } finally {
            logger.info("线程: {} 提交 ack", threadName);
            ack.acknowledge();
        }
    }
}
