package com.javamain.kafkaClient.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class vm02_consumer2assign_main {

    public static void main(String[] args) {
        Properties properties = new Properties();

        //{"enable.auto.commit":"false","consumer.id":"kloakmms01-sjc1","value.deserializer":"org.apache.kafka.common.serialization.ByteArrayDeserializer","group.id":"kloak-mirrormaker-test","fetch.message.max.bytes":"8388608","auto.offset.reset":"latest","bootstrap.servers":"vm01.com:9093","commit.zookeeper.connect":"vm01.com:2181/vmkafka010","zookeeper.session.timeout.ms":"30000","zookeeper.connect":"vm01.com:2181/vmkafka010","zookeeper.connection.timeout.ms":"30000","socket.receive.buffer.bytes":"1048576","client.id":"ureplicator-null-null-null-1","key.deserializer":"org.apache.kafka.common.serialization.ByteArrayDeserializer","queued.max.message.chunks":"5"}

        properties.put("session.timeout.ms","30000");
        properties.put("bootstrap.servers", "vm01.com:9093");
        properties.put("client.id","yzhouclientid02");
        properties.setProperty("group.id", "yzhougroupid2019101111232");
        properties.put("enable.auto.commit", "false");
        properties.put("auto.commit.interval.ms", "1000");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");

//        properties.setProperty("zookeeper.connect", "vm01.com:2181/vmkafka010");
//        properties.setProperty("zookeeper.connection.timeout.ms", "30000");
//        properties.setProperty("socket.receive.buffer.bytes", "1048576");
//        properties.setProperty("auto.offset.reset", "latest");
//        properties.setProperty("queued.max.message.chunks","5");
//        properties.setProperty("commit.zookeeper.connect","vm01.com:2181/vmkafka010");
//        properties.setProperty("fetch.message.max.bytes","8388608");





        String topic = "yzhoutest01";

        KafkaConsumer<byte[],byte[]> kafkaConsumer = new KafkaConsumer<byte[], byte[]>(properties);

        List<TopicPartition> partitions = new ArrayList<>();
        List<PartitionInfo> partitionInfos = kafkaConsumer.partitionsFor(topic);
        if(partitionInfos!=null){
            for(PartitionInfo tpInfo:partitionInfos){
                partitions.add(new TopicPartition(tpInfo.topic(),tpInfo.partition()));
            }
        }
        kafkaConsumer.assign(partitions);

        while(true) {
            ConsumerRecords<byte[],byte[]> records = kafkaConsumer.poll(1000);
            for(ConsumerRecord<byte[],byte[]> record : records){
                System.out.println(new String(record.value()));
            }
            kafkaConsumer.commitAsync();
        }


    }

}

