package com.javamain.flink;

import org.apache.flink.kubernetes.operator.api.FlinkDeployment;
import org.apache.flink.kubernetes.operator.api.FlinkSessionJob;
import org.apache.flink.kubernetes.operator.api.listener.FlinkResourceListener;
import org.apache.flink.kubernetes.operator.api.status.FlinkDeploymentStatus;
import org.apache.flink.kubernetes.operator.api.status.FlinkSessionJobStatus;
import org.apache.flink.kubernetes.operator.listener.AuditUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class MyFlinkResourceListener implements FlinkResourceListener {
    private static final Logger logger = LoggerFactory.getLogger(MyFlinkResourceListener.class);

    @Override
    public void onDeploymentStatusUpdate(StatusUpdateContext<FlinkDeployment, FlinkDeploymentStatus> statusUpdateContext) {
        // logger.info("MyFlinkResourceListener onDeploymentStatusUpdate");
        FlinkDeployment flinkResource = statusUpdateContext.getFlinkResource();
        FlinkDeploymentStatus previousStatus = statusUpdateContext.getPreviousStatus();
        FlinkDeploymentStatus newStatus = statusUpdateContext.getNewStatus();
        Instant timestamp = statusUpdateContext.getTimestamp();

        printlnFlinkDeploymentJobStatus("FlinkDeployment previousStatus", flinkResource, previousStatus);
        printlnFlinkDeploymentJobStatus("FlinkDeployment newStatus", flinkResource, newStatus);
    }

    private void printlnFlinkDeploymentJobStatus(String tag, FlinkDeployment flinkResource, FlinkDeploymentStatus jobStatus) {
        String namespace = flinkResource.getMetadata().getNamespace();
        String jobName = flinkResource.getMetadata().getName(); // NAME
        String state = jobStatus.getJobStatus().getState(); // JOB STATUS
        String lifecycleState = jobStatus.getLifecycleState().name(); // LIFECYCLE STATE
        logger.info("TAG: {}, NAMESPACE: {}, NAME: {}, JOB STATUS: {}, LIFECYCLE STATE: {}",
                tag,
                namespace,
                jobName,
                state,
                lifecycleState);
    }


    @Override
    public void onDeploymentEvent(ResourceEventContext<FlinkDeployment> resourceEventContext) {
        AuditUtils.logContext(resourceEventContext);
    }

    @Override
    public void onSessionJobStatusUpdate(StatusUpdateContext<FlinkSessionJob, FlinkSessionJobStatus> statusUpdateContext) {
        FlinkSessionJob flinkResource = statusUpdateContext.getFlinkResource();
        FlinkSessionJobStatus previousStatus = statusUpdateContext.getPreviousStatus();
        FlinkSessionJobStatus newStatus = statusUpdateContext.getNewStatus();
        Instant timestamp = statusUpdateContext.getTimestamp();

        printlnFlinkSessionJobStatus("FlinkSessionJob previousStatus", flinkResource, previousStatus);
        printlnFlinkSessionJobStatus("FlinkSessionJob newStatus", flinkResource, newStatus);
    }

    private void printlnFlinkSessionJobStatus(String tag, FlinkSessionJob flinkResource, FlinkSessionJobStatus jobStatus) {
        String namespace = flinkResource.getMetadata().getNamespace();
        String jobName = flinkResource.getMetadata().getName(); // NAME
        String state = jobStatus.getJobStatus().getState(); // JOB STATUS
        String lifecycleState = jobStatus.getLifecycleState().name(); // LIFECYCLE STATE
        logger.info("TAG: {}, NAMESPACE: {}, NAME: {}, JOB STATUS: {}, LIFECYCLE STATE: {}",
                tag,
                namespace,
                jobName,
                state,
                lifecycleState);
    }

    @Override
    public void onSessionJobEvent(ResourceEventContext<FlinkSessionJob> resourceEventContext) {
        AuditUtils.logContext(resourceEventContext);
    }
}
