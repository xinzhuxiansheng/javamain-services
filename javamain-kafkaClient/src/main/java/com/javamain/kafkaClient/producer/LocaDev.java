package com.javamain.kafkaClient.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class LocaDev {

    public static void main(String[] args) throws InterruptedException {

        Properties properties = new Properties();
<<<<<<< HEAD
        properties.put("bootstrap.servers", "192.168.0.201:9092");
=======
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("client.id", "yzhouacl0000001");
>>>>>>> 6d96e7e0c1420f234e11ca49b02863b7c5f79a7c
        properties.put("retries", 2); // 发送失败的最大尝试次数
        properties.put("batch.size", "1048576"); // 1MB
        properties.put("linger.ms", "5");
        properties.put("buffer.memory", "67108864");// 64MB
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String,String> producer = new KafkaProducer<String, String>(properties);

        Long i = 0L;
        while(true){
            String data = i+"\\t2\\tAndroid\\t869830039316690\\thuawei\\t0\\tarmeabi-v7a\\t9\\t10.1.5\\tHWI-AL00\\tHUAWEI";
<<<<<<< HEAD
            producer.send(new ProducerRecord<String, String>("yzhoujsontp02",data), new Callback() {
=======
            producer.send(new ProducerRecord<String, String>("pipeline_yzegus",data), new Callback() {
>>>>>>> 6d96e7e0c1420f234e11ca49b02863b7c5f79a7c
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(null == recordMetadata){
                        e.printStackTrace();
                    }
                }
            });
            Thread.currentThread().sleep(1000L);
            System.out.println(i);
            i++;
        }

    }
}
