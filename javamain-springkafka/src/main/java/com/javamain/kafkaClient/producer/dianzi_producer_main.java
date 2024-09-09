package com.javamain.kafkaClient.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class dianzi_producer_main {

    public static void main(String[] args) throws InterruptedException {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "xx.yy.99.152:9092");
        properties.put("retries", 2); // 发送失败的最大尝试次数
        properties.put("batch.size", "1048576"); // 1MB
        properties.put("compression.type", "gzip");
        properties.put("linger.ms", "5"); // 最长延迟5秒必须发送
        properties.put("buffer.memory", "67108864");// 64MB
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String,String> producer = new KafkaProducer<String, String>(properties);

        Long i = 0L;
//        while(true){
//            String data = i+"\\t2\\tAndroid\\t869830039316690\\thuawei\\t0\\tarmeabi-v7a\\t9\\t10.1.5\\tHWI-AL00\\tHUAWEI";
//            producer.send(new ProducerRecord<String, String>("task_change", i.toString(),data), new Callback() {
//                @Override
//                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
//                    if(null == recordMetadata){
//                        e.printStackTrace();
//                    }
//                }
//            });
//            Thread.currentThread().sleep(1000L);
//            System.out.println(data);
//            i++;
//        }

        String data = "{\"push_app_key\":\"eb3a9c9ebd7ba7398d76ee345c801307\",\"key_inside_linkid_id\":\"7890123456789012345678901234567890\",\"key_outside_pvareaid_id\":\"4567890123456789012345678901234567890123456789022\",\"key_is_substitution\":0,\"key_supply_business_id\":26,\"key_private_flag\":0,\"key_type_id\":2,\"key_name\":\"auto_testing\",\"key_phone\":\"18611010001\",\"key_distributor_id\":\"\",\"key_car_img_url\":\"\",\"key_order_city_id\":999999,\"key_release_id\":\"\",\"key_order_time\":\"2019-11-06 09:12:00\",\"key_purpose_brand_id\":51,\"key_purpose_factory_id\":56,\"key_car_audi_id\":794,\"key_car_type_id\":5186,\"split_code\":1,\"key_car_source_type\":35848,\"key_car_mileage\":1000,\"key_card_city_id\":110100,\"key_first_card_time\":\"2016-05-08 09:12:00\",\"key_purpose_price\":10000,\"key_purpose_buying_time\":1,\"key_userid\":2,\"key_is_loan\":0,\"key_client_order_ip\":\"10.167.41.139\",\"key_used_car_source_city_id\":110100,\"key_used_car_source_id\":110100,\"key_used_car_business_id\":\"\",\"key_auto_testing\":true}";
            producer.send(new ProducerRecord<String, String>("task_change", i.toString(),data), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(null == recordMetadata){
                        e.printStackTrace();
                    }
                }
            });
            Thread.currentThread().sleep(10000L);
            System.out.println(data);
    }
}
