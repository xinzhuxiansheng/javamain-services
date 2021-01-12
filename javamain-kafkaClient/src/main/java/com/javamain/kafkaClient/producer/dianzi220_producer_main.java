package com.javamain.kafkaClient.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class dianzi220_producer_main {

    public static void main(String[] args) throws InterruptedException {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "xx.yy.100.220:9092");
        properties.put("client.id","0be5bcb3750245f9b97374830fe58dfb");
        properties.put("retries", 2); // 发送失败的最大尝试次数
        properties.put("batch.size", "1048576"); // 1MB
        properties.put("compression.type", "gzip");
        properties.put("linger.ms", "5"); // 最长延迟5秒必须发送
        properties.put("buffer.memory", "67108864");// 64MB
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String,String> producer = new KafkaProducer<String, String>(properties);

        Long i = 0L;
        while(true){
            String data = i+"\\t2\\tAndroid\\t869830039316690\\thuawei\\t0\\tarmeabi-v7a\\t9\\t10.1.5\\tHWI-AL00\\tHUAWEI";
            producer.send(new ProducerRecord<String, String>("dsp_update_units_test", i.toString(),data), new Callback() {
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
