package com.javamain.flink.jobstatus.k8s;

import com.javamain.flink.jobstatus.JobStatusWatch;
import io.fabric8.kubernetes.client.CustomResource;
import org.apache.flink.kubernetes.operator.api.status.FlinkDeploymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlinkonK8sUtils {
    private static final Logger logger = LoggerFactory.getLogger(FlinkonK8sUtils.class);

    /**
     * 获取 flinkDeployments
     */
    public static void listFlinkDeployments() {
        listFlinkCRD(JobDeployConstants.Name_Space, FlinkDeploymentCRD.class);
    }

    /**
     * 获取 flinkSessionJobs
     */
    public static void listFlinkSessionJobs() {
        listFlinkCRD(JobDeployConstants.Name_Space, FlinkSessionJobCRD.class);
    }

    public static <T extends CustomResource> void watchFlinkJobStatus(String namespace, Class<T> resourceClass, JobStatusWatch jobStatusWatch){
        K8sClientSingleton.getKubernetesClient()
                .resources(resourceClass)
                .inNamespace(namespace)
                .watch(jobStatusWatch);
    }

    private static <T extends CustomResource> void listFlinkCRD(String namespace, Class<T> resourceClass) {
        K8sClientSingleton.getKubernetesClient()
                .resources(resourceClass)
                .inNamespace(namespace)
                .list()
                .getItems().forEach(item -> {
                    FlinkDeploymentStatus status = (FlinkDeploymentStatus) item.getStatus();
                    logger.info("NAMESPACE: {}, NAME: {}, JOB STATUS: {}, LIFECYCLE STATE: {}",
                            namespace,
                            item.getMetadata().getName(),
                            status.getJobStatus().getState(),
                            status.getLifecycleState().name());
                });
    }

}
