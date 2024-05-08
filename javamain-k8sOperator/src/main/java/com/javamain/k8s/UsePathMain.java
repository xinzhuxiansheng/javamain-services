package com.javamain.k8s;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;

public class UsePathMain {
    public static void main(String[] args) throws InterruptedException {


        KubernetesClient client = new KubernetesClientBuilder().build();
        NamespaceList myNs = client.namespaces().list();
        for (Namespace item : myNs.getItems()) {
            System.out.println(item.toString());
        }


        System.out.println("等待结束");
        Thread.sleep(10000000);
    }
}
