package com.javamain.flink.jobstatus;

import com.javamain.flink.jobstatus.enumeration.FlinkCRDTypeEnum;
import com.javamain.flink.jobstatus.k8s.FlinkDeploymentCRD;
import com.javamain.flink.jobstatus.k8s.FlinkonK8sUtils;
import com.javamain.flink.jobstatus.k8s.JobDeployConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class JobStatusManager {
    private static final Logger logger = LoggerFactory.getLogger(JobStatusManager.class);
    Map<FlinkCRDTypeEnum, Boolean> jobWatcherMap;

    public JobStatusManager() {
        jobWatcherMap = new HashMap<>();
    }

    private boolean isLeader() {
        return true;
    }

    public void startJobStatusMonitor() {
        FlinkonK8sUtils.watchFlinkJobStatus(
                JobDeployConstants.Name_Space,
                FlinkDeploymentCRD.class,
                new JobStatusWatch<FlinkDeploymentCRD>(
                        FlinkCRDTypeEnum.FLINKDEPLOYMENT,
                        (action, flinkDeployment) -> {
                            System.out.println("Received event: " + action + " for pod: ");
                            // 这里可以通知外部系统或更新数据库等操作

                        },
                        (flinkCRDTypeEnum) -> {
                            unregisterJobWatcher((FlinkCRDTypeEnum) flinkCRDTypeEnum);
                        }
                ));
        registerJobWatcher(FlinkCRDTypeEnum.FLINKDEPLOYMENT);
    }

    private void registerJobWatcher(FlinkCRDTypeEnum flinkCRDTypeEnum) {
        this.jobWatcherMap.put(flinkCRDTypeEnum, true);
        logger.info("{} jobStatusWatcher register",flinkCRDTypeEnum.getCode());
    }

    private void unregisterJobWatcher(FlinkCRDTypeEnum flinkCRDTypeEnum) {
        this.jobWatcherMap.remove(flinkCRDTypeEnum);
    }


}
