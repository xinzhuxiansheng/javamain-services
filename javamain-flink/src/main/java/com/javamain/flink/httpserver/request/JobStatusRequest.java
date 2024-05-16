package com.javamain.flink.httpserver.request;

import lombok.Data;

@Data
public class JobStatusRequest {
    private String app;
    private String namespace;
    private String jobStatus;
}
