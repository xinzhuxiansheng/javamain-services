package com.javamain.flink.jobstatus.akkacluster;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.javamain.flink.jobstatus.akkacluster.eventmessage.SimpleEventMessages;
import com.javamain.flink.jobstatus.enumeration.FlinkCRDTypeEnum;
import com.javamain.flink.jobstatus.k8s.*;

import java.util.HashMap;
import java.util.Map;

public class JobStatusManager extends AbstractActor {
    //    private static final Logger logger = LoggerFactory.getLogger(JobStatusManager.class);
    private final LoggingAdapter logger = Logging.getLogger(getContext().getSystem(), this);
    Map<FlinkCRDTypeEnum, io.fabric8.kubernetes.client.Watch> jobWatcherMap;

    public JobStatusManager() {
        jobWatcherMap = new HashMap<>();
    }

    @Override
    public void postStop() {
        logger.info("JobStatusManager postStop.");
        if (!jobWatcherMap.isEmpty()) {
            close();
        }
    }

    /*
        启动 watch时，也会触发 ADDED 事件，所以它弥补了，服务重启时,Job 状态的监听捕获丢失数据。
     */
    public void startJobStatusMonitor() {
        if (!jobWatcherMap.isEmpty()) {
            logger.info("JobStatusManager does not start repeatedly");
            return;
        }

        // 监听 FlinkDeployment Job
        startFlinkDeploymentJobListen();
        // 监听 FlinkSession Job
        startFlinkSessionJobListen();
    }

    private void startFlinkDeploymentJobListen() {
        io.fabric8.kubernetes.client.Watch deploymentCRDWatch = FlinkonK8sUtils.watchFlinkJobStatus(
                JobDeployConstants.Name_Space,
                FlinkDeploymentCRD.class,
                new JobStatusWatch<FlinkDeploymentCRD>(
                        FlinkCRDTypeEnum.FLINKDEPLOYMENT,
                        (action, flinkDeployment) -> {
                            System.out.println("Received event: " + action + " for pod: ");
                            // 这里可以通知外部系统或更新数据库等操作

                        },
                        (flinkCRDTypeEnum) -> {
                            unregisterJobWatcher(flinkCRDTypeEnum);
                        }
                ));
        registerJobWatcher(FlinkCRDTypeEnum.FLINKDEPLOYMENT, deploymentCRDWatch);
    }

    private void startFlinkSessionJobListen() {
        io.fabric8.kubernetes.client.Watch sessionJobCRDWatch = FlinkonK8sUtils.watchFlinkJobStatus(
                JobDeployConstants.Name_Space,
                FlinkSessionJobCRD.class,
                new JobStatusWatch<FlinkDeploymentCRD>(
                        FlinkCRDTypeEnum.FLINKSESSIONJOB,
                        (action, flinkDeployment) -> {
                            System.out.println("Received event: " + action + " for pod: ");
                            // 这里可以通知外部系统或更新数据库等操作

                        },
                        (flinkCRDTypeEnum) -> {
                            unregisterJobWatcher(flinkCRDTypeEnum);
                        }
                ));
        registerJobWatcher(FlinkCRDTypeEnum.FLINKSESSIONJOB, sessionJobCRDWatch);
    }

    public void close() {
        jobWatcherMap.forEach((key, watch) -> {
            watch.close();
        });
        jobWatcherMap.clear();
        logger.info("JobStatusManager closed ...");
    }

    private void registerJobWatcher(FlinkCRDTypeEnum flinkCRDTypeEnum, io.fabric8.kubernetes.client.Watch watch) {
        this.jobWatcherMap.put(flinkCRDTypeEnum, watch);
        logger.info("{} jobStatusWatcher register", flinkCRDTypeEnum.getCode());
    }

    /*
        jobWath 执行 它的 onClose()
     */
    private void unregisterJobWatcher(FlinkCRDTypeEnum flinkCRDTypeEnum) {
        this.jobWatcherMap.remove(flinkCRDTypeEnum);
        // 重新 JobWatch
        logger.info("JobStatusManager {} restart", flinkCRDTypeEnum.getCode());
        if (flinkCRDTypeEnum.equals(FlinkCRDTypeEnum.FLINKDEPLOYMENT)) {
            startFlinkDeploymentJobListen();
        } else {
            startFlinkSessionJobListen();
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SimpleEventMessages.StartJobStatusManager.class, event -> {
                    startJobStatusMonitor();
                }).match(SimpleEventMessages.StopJobStatusManager.class, event -> {
                    close();
                }).build();
    }
}
