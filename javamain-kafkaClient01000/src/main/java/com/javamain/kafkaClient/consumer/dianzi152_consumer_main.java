package com.javamain.kafkaClient.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class dianzi152_consumer_main {

    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.put("session.timeout.ms","30000");
        properties.put("bootstrap.servers", "xx.xxx.99.152:9092");
        properties.put("client.id","dc");
        properties.setProperty("group.id", "yzhougroupid2019101102");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList("kafkacabtp01"));

        while(true) {
            ConsumerRecords<String,String> records = kafkaConsumer.poll(10000);
            for(ConsumerRecord<String,String> record : records){
                System.out.println(record.value());
            }
        }


    }

}

