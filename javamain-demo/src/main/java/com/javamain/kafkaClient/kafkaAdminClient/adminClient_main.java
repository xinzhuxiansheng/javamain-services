package com.javamain.kafkaClient.kafkaAdminClient;

import com.javamain.kafkaClient.common.Kafka_Info;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class adminClient_main {

    public static void main(String[] args) {
        //manager kafka topics

        //Create topic
        CreateTopic("topic01");

        //Delete topic

        //List all available topics


    }


    public static void CreateTopic(String topic) {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, Kafka_Info.kafka_brokerservers);
        properties.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        AdminClient client = AdminClient.create(properties);

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
