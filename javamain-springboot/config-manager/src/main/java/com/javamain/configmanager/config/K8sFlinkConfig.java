package com.javamain.configmanager.config;

/**
 * @author yzhou
 * @date 2022/5/10
 */
public class K8sFlinkConfig {
    public static InternalOption jobStatusTrkTaskTimeoutSec = InternalOption.builder()
            .key("kubernetes.taskmanager.cpu")
            .defaultValue(120L)
            .inputFormat("-Dkubernetes.taskmanager.cpu")
            .classType(Long.TYPE)
            .description("kubernetes taskmanager cpu")
            .build();
}
