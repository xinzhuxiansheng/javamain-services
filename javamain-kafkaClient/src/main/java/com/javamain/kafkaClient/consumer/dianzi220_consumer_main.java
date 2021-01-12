package com.javamain.kafkaClient.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class dianzi220_consumer_main {

    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.put("bootstrap.servers", "10.27.14.151:9092");
        properties.put("client.id","dc");
        properties.setProperty("group.id", "yzhougroupid20191114031");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList("binlog_test_yz_10_27_14_131_item_syncdata_mall_automall_item_detail"));

        while(true) {
            ConsumerRecords<String,String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
            for(ConsumerRecord<String,String> record : records){
                System.out.println(record.value());
            }
        }


    }

}

