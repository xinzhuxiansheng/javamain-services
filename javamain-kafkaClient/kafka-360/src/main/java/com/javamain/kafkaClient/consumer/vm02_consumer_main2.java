package com.javamain.kafkaClient.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class vm02_consumer_main2 {

    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.put("bootstrap.servers", "vm02.com:9082");
        properties.put("client.id","yzhouclientid01");
        properties.setProperty("group.id", "yzhougroupid201912023");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.offset.reset", "earliest");
        properties.put("auto.commit.interval.ms", "1000");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList("heartbeats"));

        while(true) {
            ConsumerRecords<String,String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
            for(ConsumerRecord<String,String> record : records){
                System.out.println(record.value());
            }
        }


    }

}

