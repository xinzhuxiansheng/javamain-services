package com.javamain.flink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.apache.flink.kubernetes.operator.api.FlinkDeployment;
import org.apache.flink.kubernetes.operator.api.spec.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {

        // 拼接 YAML 文件
        FlinkDeployment flinkDeployment = new FlinkDeployment();
        flinkDeployment.setApiVersion("flink.apache.org/v1beta1");
        flinkDeployment.setKind("FlinkDeployment");

        ObjectMeta objectMeta = new ObjectMeta();
        objectMeta.setNamespace("flink");
        objectMeta.setName("basic-application-deployment-only-ingress");
        flinkDeployment.setMetadata(objectMeta);

        FlinkDeploymentSpec flinkDeploymentSpec = new FlinkDeploymentSpec();
        flinkDeploymentSpec.setFlinkVersion(FlinkVersion.v1_17);
        flinkDeploymentSpec.setImage("flink:1.17");

        IngressSpec ingressSpec = new IngressSpec();
        ingressSpec.setTemplate("flink.k8s.io/{{namespace}}/{{name}}(/|$)(.*)");
        ingressSpec.setClassName("nginx");
        Map<String, String> annotations = new HashMap<>();
        annotations.put("nginx.ingress.kubernetes.io/rewrite-target","/$2");
        ingressSpec.setAnnotations(annotations);
        flinkDeploymentSpec.setIngress(ingressSpec);

        Map<String, String> flinkConfiguration = new HashMap<>();
        flinkConfiguration.put("taskmanager.numberOfTaskSlots", "2");
        flinkDeploymentSpec.setFlinkConfiguration(flinkConfiguration);
        flinkDeploymentSpec.setServiceAccount("flink");
        JobManagerSpec jobManagerSpec = new JobManagerSpec();
        jobManagerSpec.setResource(new Resource(1.0, "2048m","2G"));
        flinkDeploymentSpec.setJobManager(jobManagerSpec);
        TaskManagerSpec taskManagerSpec = new TaskManagerSpec();
        taskManagerSpec.setResource(new Resource(1.0, "2048m","2G"));
        flinkDeploymentSpec.setTaskManager(taskManagerSpec);
        flinkDeployment.setSpec(flinkDeploymentSpec);
        flinkDeployment
                .getSpec()
                .setJob(
                        JobSpec.builder()
                                .jarURI(
                                        "local:///opt/flink/examples/streaming/StateMachineExample.jar")
                                .parallelism(2)
                                .upgradeMode(UpgradeMode.STATELESS)
                                .build());

        // 打印 内容
        String yaml = toYaml(flinkDeployment);
        System.out.println("打印 Flink Job YAML");
        System.out.println(yaml);

        // 提交 Job
        try (KubernetesClient kubernetesClient = new KubernetesClientBuilder().build()) {
            kubernetesClient.resource(flinkDeployment).createOrReplace();
        }

        System.out.println("Job 提交结束");
    }

    public static String toYaml(Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();  // Registers all available modules including Java Time modules
        return mapper.writeValueAsString(obj);
    }
}
