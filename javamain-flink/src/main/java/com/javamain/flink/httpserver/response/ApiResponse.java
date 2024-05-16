package com.javamain.flink.httpserver.response;

import lombok.Data;

@Data
public class ApiResponse {
    private int code;
    private String message;
    private Object data;

    public ApiResponse() {
    }

    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
