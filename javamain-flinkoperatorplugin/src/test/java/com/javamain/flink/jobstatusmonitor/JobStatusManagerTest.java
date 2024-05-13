package com.javamain.flink.jobstatusmonitor;

import com.javamain.flink.jobstatus.akkacluster.JobStatusManager;
import org.junit.Test;

import java.io.IOException;

public class JobStatusManagerTest {

    @Test
    public void testJobStatusMonitor() throws IOException {
        JobStatusManager jobStatusManager = new JobStatusManager();
        jobStatusManager.startJobStatusMonitor();
        System.out.println("jobStatusManager start ...");

        System.out.println("press ENTER to terminate");
        System.in.read();
    }
}
