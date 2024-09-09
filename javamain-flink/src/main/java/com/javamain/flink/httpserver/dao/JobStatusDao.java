package com.javamain.flink.httpserver.dao;

import com.javamain.flink.httpserver.model.JobStatus;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobStatusDao {
    private static final Logger logger = LoggerFactory.getLogger(JobStatusDao.class);
    private final static List<JobStatus> jobStatusList = new ArrayList<>();

    static {
        jobStatusList.add(new JobStatus("flink-job-01", "flink", "RUNNING"));
        jobStatusList.add(new JobStatus("flink-job-02", "flink", "RUNNING"));
        jobStatusList.add(new JobStatus("flink-job-03", "flink", "RUNNING"));
        jobStatusList.add(new JobStatus("flink-job-04", "flink", "RUNNING"));
        jobStatusList.add(new JobStatus("flink-job-05", "flink", "RUNNING"));
    }

    public Optional<JobStatus> getJobStatus(String app) throws InterruptedException {
        logger.info("JobStatusDao getJobStatus !!!! time: {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
        Optional<JobStatus> jobStatus =  jobStatusList.stream()
                .filter(js -> js.getApp().equals(app))
                .findFirst();
//        Thread.sleep(5000);
        return jobStatus;
    }

    public void pushJobStatus(JobStatus jobStatus) {
        jobStatusList.add(jobStatus);
    }

    public List<JobStatus> getJobStatus() {
        return jobStatusList;
    }
}
