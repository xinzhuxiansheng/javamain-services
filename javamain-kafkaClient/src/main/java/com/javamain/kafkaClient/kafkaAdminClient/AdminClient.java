package com.javamain.kafkaClient.kafkaAdminClient;

import com.javamain.kafkaClient.common.Kafka_Info;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class AdminClient {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //manager kafka topics

        //Create topic
        //CreateTopic("yzhoutp01");

        //Delete topic

        //List all available topics


        //列举 consumer
        //listConsuemrGroup();

        setConsumerGroupIdAtTimeStamp("10.168.100.16:9093",
                "D://commandconfig.properties",
                "yzhougid2020092901",
                "yzhoutpA001",
                "2020-09-29T00:00:00.000");
    }

    public static org.apache.kafka.clients.admin.AdminClient initAdminClient(){
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, Kafka_Info.kafka_brokerservers);
        properties.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        org.apache.kafka.clients.admin.AdminClient client = org.apache.kafka.clients.admin.AdminClient.create(properties);
        return client;
    }

    public static void listConsuemrGroup() throws ExecutionException, InterruptedException {
        org.apache.kafka.clients.admin.AdminClient client = initAdminClient();
        ListConsumerGroupsResult listConsumerGroupsResult =  client.listConsumerGroups();
        KafkaFuture<Collection<ConsumerGroupListing>> collectionKafkaFuture =  listConsumerGroupsResult.all();
        Collection<ConsumerGroupListing> list = collectionKafkaFuture.get();
        for(ConsumerGroupListing consumerGroupListing:list){
            System.out.println(consumerGroupListing.groupId());
        }
        System.out.println("结束");
        client.close();
    }



    public static void CreateTopic(String topic) {

        org.apache.kafka.clients.admin.AdminClient client = initAdminClient();

        NewTopic newTopic = new NewTopic(topic, 1, (short) 1);
        Map<String,String> configs = new HashMap<>();

        //压缩策略为cleanup.policy=compact的情况下，key不能为空
        newTopic.configs(configs);

        CreateTopicsResult result = client.createTopics(Collections.singleton(newTopic));

        try {
            result.all().get();
        } catch (InterruptedException | ExecutionException e) {
           e.printStackTrace();
        }
        client.close();
    }


    public static void setConsumerGroupIdAtTimeStamp(String bootstrapServers,
                                                     String propertiesFilePath,
                                                     String groupId,
                                                     String topicName,
                                                     String dateTime){
        String args[] = {
                "--bootstrap-server",
                bootstrapServers,
                "--command-config",
                propertiesFilePath,
                "--group",
                groupId,
                "--reset-offsets",
                "--topic",
                topicName,
                "--to-datetime",
                dateTime,
                "--execute"
        };
        kafka.admin.ConsumerGroupCommand.main(args);
    }

}
