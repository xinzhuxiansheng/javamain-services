package com.javamain.k8s;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.NamespacedKubernetesClient;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        String kubeContext = null;
        Config config = Config.autoConfigure(kubeContext);
        final NamespacedKubernetesClient client = new DefaultKubernetesClient(config);
        NamespaceList myNs = client.namespaces().list();
        for (Namespace item : myNs.getItems()) {
            System.out.println(item.toString());
        }


        System.out.println("等待结束");
        Thread.sleep(10000000);
    }
}
