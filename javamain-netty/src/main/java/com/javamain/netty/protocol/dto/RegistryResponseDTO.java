package com.javamain.netty.protocol.dto;

public class RegistryResponseDTO {
    private int status;
    private String msgId;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
