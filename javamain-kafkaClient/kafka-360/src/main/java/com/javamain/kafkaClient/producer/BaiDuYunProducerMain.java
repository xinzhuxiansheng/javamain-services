package com.javamain.kafkaClient.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class BaiDuYunProducerMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.0.202:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(properties);
        Long i = 0L;
        //while(true){
        String data = i + "样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样" +
                "样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样" +
                "样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样" +
                "样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样" +
                "样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样" +
                "样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样" +
                "样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样" +
                "样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样" +
                "样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样" +
                "样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样" +
                "样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样" +
                "样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样" +
                "例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据样例数据！";
        System.out.println("data size: " + data.getBytes().length);
        producer.send(new ProducerRecord<String, String>("yzhoujsontp01", data), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (null == recordMetadata) {
                    e.printStackTrace();
                }
            }
        }).get();
        Thread.currentThread().sleep(1000L);
        i++;
        // }
        System.out.println("end !");
    }
}
