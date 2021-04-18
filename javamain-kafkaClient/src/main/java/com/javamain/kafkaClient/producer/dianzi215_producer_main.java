package com.javamain.kafkaClient.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class dianzi215_producer_main {

    public static void main(String[] args) throws InterruptedException {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "xx.xxx.100.16:9093,xx.xxx.100.17:9093,xx.xxx.100.18:9093");
        properties.put("client.id","87704768d320456888e372529598e731");
        properties.put("retries", 2); // 发送失败的最大尝试次数
        properties.put("batch.size", "1048576"); // 1MB
        properties.put("compression.type", "gzip");
        properties.put("linger.ms", "5");
        properties.put("buffer.memory", "67108864");// 64MB
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String,String> producer = new KafkaProducer<String, String>(properties);

        Long i = 0L;
        while(true){
            String data = i+"\\t2\\tAndroid\\t869830039316690\\thuawei\\t0\\tarmeabi-v7a\\t9\\t10.1.5\\tHWI-AL00\\tHUAWEI";
            producer.send(new ProducerRecord<String, String>("nlp_uniq_offline_dev",data), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(null == recordMetadata){
                        e.printStackTrace();
                    }
                }
            });
            Thread.currentThread().sleep(1000L);
            System.out.println(data);
            i++;
        }

    }
}
