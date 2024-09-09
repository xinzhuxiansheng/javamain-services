package com.javamain.kafkaClient.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class vm02_consumer_main {

    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.put("session.timeout.ms","30000");
        properties.put("bootstrap.servers", "vm02.com:9082");
        properties.put("client.id","yzhouclientid0111111");
        properties.setProperty("group.id", "aaaabbbbcccc");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList("hyvm01.yzhoutp01"));

        while(true) {
            ConsumerRecords<String,String> records = kafkaConsumer.poll(1000);
            for(ConsumerRecord<String,String> record : records){
                System.out.println("offset: "+ record.offset() +"  value: "+record.value());
            }
        }


    }

}

