package com.javamain.kafkaClient.yt;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class dianzi215_producer_main {

    public static void main(String[] args) throws InterruptedException {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("retries", 2);
        properties.put("batch.size", "1048576");
        properties.put("compression.type", "gzip");
        properties.put("linger.ms", "1000");
        properties.put("buffer.memory", "67108864");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(properties);

        Integer max = 1000000;
        Integer i = 0;
        while (true) {
            String data = "{\"id\":1,\"name\":\"yzhou\",\"money\":2,\"dtime01\":\"2024-04-18 12:00:00\"}";
            producer.send(new ProducerRecord<String, String>("yzhoujsontp11", data), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (null == recordMetadata) {
                        e.printStackTrace();
                    }
                }
            });
//            Thread.currentThread().sleep(10000L);
//            System.out.println(data);
            i++;
            if (i >= max) {
                break;
            }
        }

        System.out.println("结束");

    }
}
