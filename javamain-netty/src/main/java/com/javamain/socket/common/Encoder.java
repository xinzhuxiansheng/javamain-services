package com.javamain.socket.common;

import com.google.common.primitives.Bytes;

import java.util.Arrays;

/**
 * @author yzhou
 * @date 2022/12/19
 */
public class Encoder {

    public static byte[] encode(Message msg) {
        return Bytes.concat(new byte[]{0}, msg.getData());
    }

    public static Message decode(byte[] data) {
        if (data.length < 1) {
            throw new IllegalArgumentException("无效消息");
        }
        if (data[0] == 0) {
            return new Message(Arrays.copyOfRange(data, 1, data.length));
        } else {
            throw new IllegalArgumentException("无效消息");
        }
    }

}
