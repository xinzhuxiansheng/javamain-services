package com.javamain.im.groupchat.protocol.response;

import com.javamain.im.groupchat.protocol.Packet;
import lombok.Data;

import static com.javamain.im.singlechat.protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    private String userId;

    private String userName;

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}