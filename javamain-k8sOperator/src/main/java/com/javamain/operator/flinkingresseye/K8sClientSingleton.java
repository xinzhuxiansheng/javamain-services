package com.javamain.operator.flinkingresseye;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class K8sClientSingleton {
    private static final Logger logger = LoggerFactory.getLogger(K8sClientSingleton.class);
    // 暂不考虑 config
    private static final KubernetesClient KUBERNETES_CLIENT = new KubernetesClientBuilder().build();

    private K8sClientSingleton() {
    }

    public static KubernetesClient getKubernetesClient() {
        return KUBERNETES_CLIENT;
    }
}
