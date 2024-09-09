package com.javamain.kafkaClient.uReplicator;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class dockertest_consumer_main {
    private static final Logger logger = LoggerFactory.getLogger(dockertest_consumer_main.class);

    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.put("bootstrap.servers", "xx.xxx.xxx.xxx:9092");
        properties.put("client.id","yzhouclientid01");
        properties.setProperty("group.id", "docker2019120304");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList("uReplicator_yzhou01"));



        while(true) {
            ConsumerRecords<String,String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
            for(ConsumerRecord<String,String> record : records){

                String keydate = new DateTime(Long.valueOf(record.value())).toString("yyyy-MM-dd-HH-mm");
                logger.info("dockertest_result__ {}",keydate);
                //System.out.println("p: "+record.partition()+" ,offset: "+record.offset()+" ,value: "+record.value());
            }
        }




    }

}

