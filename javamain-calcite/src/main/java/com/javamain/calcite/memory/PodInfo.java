package com.javamain.calcite.memory;

import lombok.Data;

@Data
public class PodInfo {
    private String namespace;
    private String podName;
    private String ready;
    private String status;
    private int restartCount;
    private String age;
    private String podIP;
    private String node;

    public PodInfo() {}

    public PodInfo(String namespace, String podName, String ready, String status, 
                   int restartCount, String age, String podIP, String node) {
        this.namespace = namespace;
        this.podName = podName;
        this.ready = ready;
        this.status = status;
        this.restartCount = restartCount;
        this.age = age;
        this.podIP = podIP;
        this.node = node;
    }

    @Override
    public String toString() {
        // 原始输出格式
        return String.format("%s\t\t%s\t\t%s\t\t%s\t\t%d\t\t%s\t\t%s\t\t%s",
                namespace, podName, ready, status, restartCount, age, podIP, node);
    }
}