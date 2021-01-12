package com.javamain.kafkaClient.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class vm01_consumer_main {

    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.put("session.timeout.ms","3000000");
        properties.put("bootstrap.servers", "localhost:9093");
        properties.put("client.id","ygid01");
        properties.setProperty("group.id", "ygid01222222");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList("yzhoutp01"));

        int i=0;
        while(true) {
            ConsumerRecords<String,String> records = kafkaConsumer.poll(10000);
            if(!records.isEmpty()){
                for(ConsumerRecord<String,String> record : records){
                    System.out.println("offset: "+ record.offset() +"  value: "+record.value());
                }
                //System.out.println("\r\n");
            }
        }


    }

}

