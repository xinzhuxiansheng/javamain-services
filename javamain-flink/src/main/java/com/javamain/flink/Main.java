package com.javamain.flink;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;

public class Main {

    public static void main(String[] args) {
        KubernetesClient client = new KubernetesClientBuilder().build();


    }
}
