package com.javamain.netty.protocol.common.enums;

public enum ServerEventCode {
    REGISTRY(1,"注册事件"),
    UN_REGISTRY(2,"下线事件"),
    HEART_BEAT(3,"心跳事件"),
    ;

    int code;
    String desc;

    ServerEventCode(int code, String desc) {
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

