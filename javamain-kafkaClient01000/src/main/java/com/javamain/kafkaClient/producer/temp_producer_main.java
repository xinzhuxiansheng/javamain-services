package com.javamain.kafkaClient.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class temp_producer_main {

    public static void main(String[] args) throws InterruptedException {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "xx.yy.66.127:9092");
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
            String data = "{\"biz_id\":33,\"biz_type\":700027,\"data\":{\"car_brand_ids\":\"165\",\"car_brand_names\":\"东风风行\",\"car_country_ids\":\"1\",\"car_country_names\":\"中国\",\"city_ids\":\"110100\",\"cms_energy_ids\":\"0\",\"cms_energy_names\":\"\",\"cms_level_ids\":17,\"cms_level_names\":\"紧凑型SUV\",\"cms_series_ids\":\"4767\",\"cms_series_names\":\"风行T5\",\"cms_spec_ids\":\"35433\",\"cms_spec_names\":\"2018款 300T DCT智享型\",\"detail_url\":\"http://dealer.m.xxxxhome.com.cn/jiajiago/goods?specId=35433&skuId=33&cityId={cityId}&eid=2%7C1211004%7C1479%7C16201%7C204018%7C302243\",\"dikouquan_price\":null,\"end_time\":null,\"factory_id\":164,\"factory_name\":\"东风风行\",\"info_obj\":null,\"jump_url\":\"{\\\"app_jump\\\":\\\"http://dealer.m.xxxxhome.com.cn/jiajiago/goods?specId=35433&skuId=33&cityId={cityId}&eid=2%7C1211004%7C1479%7C16201%7C204018%7C302243\\\",\\\"m_jump\\\":\\\"http://dealer.m.xxxxhome.com.cn/jiajiago/goods?specId=35433&skuId=33&cityId={cityId}&eid=2%7C1211004%7C1479%7C16201%7C204018%7C302243\\\",\\\"original_m_jump\\\":\\\"http://dealer.m.xxxxhome.com.cn/jiajiago/goods?specId=35433&skuId=33&cityId={cityId}\\\"}\",\"pick_city_ids\":null,\"price_template\":\"1039.00\",\"recommend_time\":\"2019-08-07 13:06:06\",\"sale_price\":\"103900.00\",\"series_attention\":0,\"spec_attention\":0,\"suggect_price\":\"123900\",\"title\":\"东风风行 风行T5 300T DCT智享型\",\"type\":102,\"used_type\":\"000001101\"},\"object_class_id\":\"0000\",\"object_line_id\":\"0000\",\"operation\":0,\"push_time\":\"2019-11-21 16:51:44\",\"tbl_id\":3005}";
            producer.send(new ProducerRecord<String, String>("mis_dealer_700027_online", i.toString(),data), new Callback() {
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
