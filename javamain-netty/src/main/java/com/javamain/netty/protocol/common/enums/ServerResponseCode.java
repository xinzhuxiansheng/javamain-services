package com.javamain.netty.protocol.common.enums;

public enum ServerResponseCode {
    ERROR_USER_OR_PASSWORD(1001,"账号验证异常"),
    UN_REGISTRY_SERVICE(1002,"服务正常下线"),
    REGISTRY_SUCCESS(1003,"注册成功"),
    ;


    int code;
    String desc;

    ServerResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
