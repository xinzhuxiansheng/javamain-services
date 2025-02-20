package com.javamain.kafkaClient.consumer;

import kafka.common.OffsetAndMetadata;
import kafka.coordinator.group.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.*;

public class ConsumerConsumerOffsetsTopic {
    public static void main(String[] args) {
        Map<String, Long> lag = new HashMap<>();

        String consumerOffsetTopic = "__consumer_offsets";
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "xx.xxx.5.87:9092");
        properties.put("client.id","dc");
        properties.setProperty("group.id", "yzhougid2020073101");
        properties.setProperty(ConsumerConfig.EXCLUDE_INTERNAL_TOPICS_CONFIG,"false");
        properties.put("enable.auto.commit", "false");
        properties.put("auto.offset.reset","latest");
        //properties.put("auto.commit.interval.ms", "1000");
        //properties.put("max.poll.records",1);
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");

        KafkaConsumer<byte[],byte[]> consumer = new KafkaConsumer<byte[], byte[]>(properties);
        consumer.subscribe(Arrays.asList(consumerOffsetTopic));
        try {
            while (true) {
                try {
                    //从服务端拉取消息，每次poll() 可以拉取多个消息
                    ConsumerRecords<byte[], byte[]> records = consumer.poll(Duration.ofSeconds(10));
                    Iterator<ConsumerRecord<byte[], byte[]>> iterator = records.iterator();
                    while (iterator.hasNext()) {
                        ConsumerRecord<byte[], byte[]> record = iterator.next();
                        ByteBuffer wrap = ByteBuffer.wrap(record.key());
                        BaseKey baseKey = GroupMetadataManager.readMessageKey(wrap);
                        if (record.value() == null) {
                            continue;
                        }
                        if (baseKey instanceof OffsetKey) {
                            ByteBuffer wrap1 = ByteBuffer.wrap(record.value());
                            OffsetAndMetadata value = GroupMetadataManager
                                    .readOffsetMessageValue(wrap1);
                            //group
                            GroupTopicPartition gtp = (GroupTopicPartition) baseKey.key();
                            String group = gtp.group();
                            if ("xxxxx.ops.kafka".equals(group)) {
                                continue;
                            }
                            //topic
                            String topic = gtp.topicPartition().topic();
                            //partition
                            int partition = gtp.topicPartition().partition();
                            //offset
                            long offset = value.offset();
                            // log.debug("======The key {} commited offset is {}=======",
                            //        group + "|" + topic + "|" + partition, offset);
                            if(group.equals("nlp_resouce_pool_distinct_consumer") && topic.equals("zhengpaiku_tb_car_pool")){
                                System.out.println(group + "\t" + topic + "\t" + partition + "\t" + offset);
                            }
                            if(group.equals("nlp_resouce_pool_distinct_consumer") && topic.equals("zhengpaiku_tb_active_pool")){
                                System.out.println(group + "\t" + topic + "\t" + partition + "\t" + offset);
                            }
                            if(group.equals("nlp_resouce_pool_distinct_consumer") && topic.equals("zhengpaiku_tb_richmedia_pool")){
                                System.out.println(group + "\t" + topic + "\t" + partition + "\t" + offset);
                            }
                            if(group.equals("nlp_resouce_pool_distinct_consumer") && topic.equals("zhengpaiku_tb_topic_pool")){
                                System.out.println(group + "\t" + topic + "\t" + partition + "\t" + offset);
                            }
                            System.out.println(group + "\t" + topic + "\t" + partition + "\t" + offset);
                            lag.put(group + "|" + topic + "|" + partition, offset);
//                        redisTemplate.opsForHash().put("cloud_kafka_commited_offset",
//                                group + "|" + topic + "|" + partition, offset);
                        } else if (baseKey instanceof GroupMetadataKey) {
                            System.out.println("============No work=========");
                        }
                    }
                    //Global.setOffset();
                    System.out.println(lag.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
            //log.info("Shutting down consumer fo r{} on cluster", consumerOffsetTopic);
            consumer.close();
        }

    }
}
