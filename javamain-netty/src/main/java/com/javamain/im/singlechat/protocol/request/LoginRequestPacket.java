package com.javamain.im.singlechat.protocol.request;

import com.javamain.im.singlechat.protocol.Packet;
import lombok.Data;

import static com.javamain.im.simple01.protocol.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {
    private String userName;

    private String password;

    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }
}
