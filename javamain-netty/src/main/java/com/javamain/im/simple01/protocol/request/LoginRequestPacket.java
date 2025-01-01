package com.javamain.im.simple01.protocol.request;

import com.javamain.im.simple01.protocol.Packet;
import lombok.Data;

import static com.javamain.im.simple01.protocol.command.Command.LOGIN_REQUEST;


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
