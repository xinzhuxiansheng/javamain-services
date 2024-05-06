package com.javamain.flink.jobstatusmonitor;

import com.javamain.flink.jobstatus.JobStatusManager;
import org.junit.Test;

public class JobStatusManagerTest {

    @Test
    public void testJobStatusMonitor() throws InterruptedException {
        JobStatusManager jobStatusManager = new JobStatusManager();
        jobStatusManager.startJobStatusMonitor();
        System.out.println("jobStatusManager start ...");

        Thread.sleep(1000000);  // 模拟长期运行场景
    }
}
