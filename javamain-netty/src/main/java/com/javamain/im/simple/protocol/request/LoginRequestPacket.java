package com.javamain.im.simple.protocol.request;

import com.javamain.im.simple.protocol.Packet;
import lombok.Data;

import static com.javamain.im.simple.protocol.command.Command.LOGIN_REQUEST;


@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
