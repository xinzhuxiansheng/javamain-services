package com.javamain.kafkaClient.consumer;

import com.javamain.kafkaClient.common.Kafka_Info;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class ConsumerTimestampMain {

    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.put("bootstrap.servers", "yz-test01-broker0-kafka.corpxxxxhome.com:9092,yz-test01-broker1-kafka.corpxxxxhome.com:9092,yz-test01-broker2-kafka.corpxxxxhome.com:9092");
        properties.put("client.id", "1e357476ee124201a655c7175cb1750e");
        properties.setProperty("group.id", "yzhougid05140672");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
//        properties.put("max.partition.fetch.bytes",100000);
//        properties.put("auto.offset.reset","earliest");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        String topic = "yzhoutp01";

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<TopicPartition> partitions = new ArrayList<>();
        Map<TopicPartition, Long> timestampsToSearch = new HashMap<>();
        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
        for (PartitionInfo partitionInfo : partitionInfos) {
            partitions.add(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()));
            timestampsToSearch.put(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()), 1592212200000L);
        }
        consumer.assign(partitions);

        OffsetAndTimestamp offsetTimestamp = null;
        Map<TopicPartition, OffsetAndTimestamp> result = consumer.offsetsForTimes(timestampsToSearch);
        for (Map.Entry<TopicPartition, OffsetAndTimestamp> entry : result.entrySet()) {
            offsetTimestamp = entry.getValue();
            if (offsetTimestamp != null) {
                int partition = entry.getKey().partition();
                long timestamp = offsetTimestamp.timestamp();
                long offset = offsetTimestamp.offset();
                System.out.println("partition = " + partition +
                        ", time = " + df.format(new Date(timestamp)) +
                        ", offset = " + offset);
                // 设置读取消息的偏移量
                consumer.seek(entry.getKey(), offset);//每个topic的partition都seek到执行的offset
            }

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record.timestamp() + " , " + record.offset() + " , " + record.value());
                }
            }
        }
    }

}

