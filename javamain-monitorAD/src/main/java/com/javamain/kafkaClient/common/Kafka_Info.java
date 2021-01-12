package com.javamain.kafkaClient.common;

public class Kafka_Info {
    private String clusterName;
    private String topicName;
    private String port;
    private String cosnumerGroup;

    public Kafka_Info(String clusterName, String topicName, String port, String cosnumerGroup) {
        this.clusterName = clusterName;
        this.topicName = topicName;
        this.port = port;
        this.cosnumerGroup = cosnumerGroup;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCosnumerGroup() {
        return cosnumerGroup;
    }

    public void setCosnumerGroup(String cosnumerGroup) {
        this.cosnumerGroup = cosnumerGroup;
    }
}
