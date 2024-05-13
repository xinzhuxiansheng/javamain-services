package com.javamain.flink;

import org.apache.flink.kubernetes.operator.api.FlinkDeployment;
import org.apache.flink.kubernetes.operator.api.FlinkSessionJob;
import org.apache.flink.kubernetes.operator.api.listener.FlinkResourceListener;
import org.apache.flink.kubernetes.operator.api.status.FlinkDeploymentStatus;
import org.apache.flink.kubernetes.operator.api.status.FlinkSessionJobStatus;

public class MyFlinkResourceListener implements FlinkResourceListener {
    @Override
    public void onDeploymentStatusUpdate(StatusUpdateContext<FlinkDeployment, FlinkDeploymentStatus> statusUpdateContext) {

    }

    @Override
    public void onDeploymentEvent(ResourceEventContext<FlinkDeployment> resourceEventContext) {

    }

    @Override
    public void onSessionJobStatusUpdate(StatusUpdateContext<FlinkSessionJob, FlinkSessionJobStatus> statusUpdateContext) {

    }

    @Override
    public void onSessionJobEvent(ResourceEventContext<FlinkSessionJob> resourceEventContext) {

    }
}
