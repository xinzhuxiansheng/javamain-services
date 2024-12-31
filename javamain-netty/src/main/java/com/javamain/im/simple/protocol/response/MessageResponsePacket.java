package com.javamain.im.simple.protocol.response;

import com.javamain.im.simple.protocol.Packet;
import lombok.Data;

import static com.javamain.im.simple.protocol.command.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}
