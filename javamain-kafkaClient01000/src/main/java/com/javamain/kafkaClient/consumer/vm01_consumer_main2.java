package com.javamain.kafkaClient.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class vm01_consumer_main2 {

    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.put("session.timeout.ms","30000");
        properties.put("bootstrap.servers", "vm01.com:9082");
        properties.put("client.id","ygid01");
        properties.setProperty("group.id", "ygid0144444444");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList("hyvm02.yzhoutp01"));

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

