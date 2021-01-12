package com.javamain.kafkaClient.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class LocalDev {

    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();

        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("client.id","dc");
        properties.setProperty("group.id", "yzhougid2020101101");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.offset.reset", "earliest");
        properties.put("auto.commit.interval.ms", "1000");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList("yzhoutp01"));

        while(true) {
            ConsumerRecords<String,String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
            for(ConsumerRecord<String,String> record : records){
                System.out.println(record.value());
                //Thread.sleep(1000L);
            }
        }


    }

}

