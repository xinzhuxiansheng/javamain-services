package com.javamain.flash.netty.chapter07;

import lombok.Data;

@Data
public abstract class Packet {
    // 协议版本
    private Byte version = 1;

    // 指令
    public abstract Byte getCommand();
}
