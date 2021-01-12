/*
package com.javamain.kafkaClient.kafkaAdminClient;

import com.javamain.kafkaClient.common.Kafka_Info;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class adminClient_main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //manager kafka topics

        //Create topic
        //CreateTopic("topic01");

        //Delete topic

        //List all available topics


        //列举 consumer
        listConsuemrGroup();

    }

    public static AdminClient initAdminClient(){
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, Kafka_Info.kafka_brokerservers);
        properties.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        AdminClient client = AdminClient.create(properties);
        return client;
    }

    public static void listConsuemrGroup() throws ExecutionException, InterruptedException {
        AdminClient client = initAdminClient();
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

        AdminClient client = initAdminClient();

        NewTopic newTopic = new NewTopic(topic, 3, (short) 1);
        Map<String,String> configs = new HashMap<>();

        //压缩策略为cleanup.policy=compact的情况下，key不能为空

        configs.put("cleanup.policy","compact");
        newTopic.configs(configs);

        CreateTopicsResult result = client.createTopics(Collections.singleton(newTopic));

        try {
            result.all().get();
        } catch (InterruptedException | ExecutionException e) {
           e.printStackTrace();
        }
        client.close();
    }



}
*/
