package com.javamain.flash.netty.chapter07;

import lombok.Data;

import static com.javamain.flash.netty.chapter07.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet{

    private Integer userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
