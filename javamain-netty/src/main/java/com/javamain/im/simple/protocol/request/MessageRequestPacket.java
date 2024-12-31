package com.javamain.im.simple.protocol.request;

import com.javamain.im.simple.protocol.Packet;
import lombok.Data;

import static com.javamain.im.simple.protocol.command.Command.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
