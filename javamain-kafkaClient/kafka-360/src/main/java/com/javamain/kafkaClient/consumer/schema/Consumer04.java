package com.javamain.kafkaClient.consumer.schema;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Consumer04 {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("args 0: " + args[0]);
        System.out.println("args 1: " + args[1]);
        System.out.println("args 2: " + args[2]);

        Properties properties = new Properties();
        properties.put("bootstrap.servers", args[0]);
        properties.setProperty("group.id", args[1]);
        properties.put("enable.auto.commit", "true");
        properties.put("auto.offset.reset", "earliest");
        properties.put("auto.commit.interval.ms", "1000");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList(args[2]));

        while(true) {
            ConsumerRecords<String,String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
            for(ConsumerRecord<String,String> record : records){
                System.out.println(record.value());
                //Thread.sleep(1000L);
            }
        }


    }
}
