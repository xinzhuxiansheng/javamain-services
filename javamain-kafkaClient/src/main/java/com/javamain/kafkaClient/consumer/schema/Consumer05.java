package com.javamain.kafkaClient.consumer.schema;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Consumer05 {
    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();

        properties.put("bootstrap.servers", "192.168.64.xxx:9092");
        properties.setProperty("group.id", "yzhougid2023091304");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.offset.reset", "earliest");
        properties.put("auto.commit.interval.ms", "1000");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        properties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, false);
        properties.put("schema.registry.url", "http://192.168.64.xxx:8081");



        KafkaConsumer<String, GenericRecord> kafkaConsumer = new KafkaConsumer<String, GenericRecord>(properties);
        kafkaConsumer.subscribe(Arrays.asList("yzhoutp05"));

        while(true) {
            ConsumerRecords<String,GenericRecord> records = kafkaConsumer.poll(Duration.ofSeconds(10));
            for(ConsumerRecord<String,GenericRecord> record : records){
                System.out.println(record.value());
            }
        }


    }
}
