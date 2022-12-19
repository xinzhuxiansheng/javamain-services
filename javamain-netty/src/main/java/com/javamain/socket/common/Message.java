package com.javamain.socket.common;

/**
 * @author yzhou
 * @date 2022/12/19
 */
public class Message {
    byte[] data;

    public Message(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}
