package com.javamain.flink.httpserver.model;


import lombok.Data;

@Data
public class JobStatus {
    private String app;
    private String namespace;
    private String jobStatus;

    public JobStatus() {
    }

    public JobStatus(String app, String namespace, String jobStatus) {
        this.app = app;
        this.namespace = namespace;
        this.jobStatus = jobStatus;
    }
}
