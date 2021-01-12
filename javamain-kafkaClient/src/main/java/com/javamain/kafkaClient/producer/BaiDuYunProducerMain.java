package com.javamain.kafkaClient.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class BaiDuYunProducerMain {

    public static void main(String[] args) throws InterruptedException {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "118.126.116.71:9093");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String,String> producer = new KafkaProducer<String, String>(properties);
        Long i = 0L;
        while(true){
            String data = i+"样例数据！";
            producer.send(new ProducerRecord<String, String>("yzhoutp01",data), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(null == recordMetadata){
                        e.printStackTrace();
                    }
                }
            });
            Thread.currentThread().sleep(1000L);
            i++;
        }

    }
}
