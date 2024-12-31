package com.javamain.im.simple.protocol.response;

import com.javamain.im.simple.protocol.Packet;
import lombok.Data;

import static com.javamain.im.simple.protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
