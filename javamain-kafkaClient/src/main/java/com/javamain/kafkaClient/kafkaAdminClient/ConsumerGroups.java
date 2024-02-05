package com.javamain.kafkaClient.kafkaAdminClient;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class ConsumerGroups {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //listConsuemrGroup();
        describeConsumerGroups();

        //Thread.sleep(500000);
    }

    public static org.apache.kafka.clients.admin.AdminClient initAdminClient(){
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
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


    public static void listConsumerGroups(){
        AdminClient adminClient = initAdminClient();

        List<String> groupIds = new ArrayList<>();
        adminClient.listConsumerGroups().all().whenComplete((mapGroup,error)->{
            mapGroup.forEach(group->{
                groupIds.add(group.groupId());
            });
        });

        DescribeConsumerGroupsOptions options = new DescribeConsumerGroupsOptions();
        options.timeoutMs(1000);
        adminClient.describeConsumerGroups(groupIds,options).all().whenComplete((mapGroupDescription,error)->{
            mapGroupDescription.forEach((group,desc)->{
                System.out.println(new String(group.getBytes()));
            });
        });
    }

    public static void describeConsumerGroups() throws ExecutionException, InterruptedException {
        AdminClient adminClient = initAdminClient();

        List<String> groupIds = new ArrayList<>();
        groupIds.add("yzhougid20201011012222");

        Map<String, ConsumerGroupDescription> result = adminClient.describeConsumerGroups(groupIds).all().get();
        System.out.println("aaa");
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

//    public static void setTimeStamp4GroupId(){
//        kafka.admin.ConsumerGroupCommand.main();
//    }


}
