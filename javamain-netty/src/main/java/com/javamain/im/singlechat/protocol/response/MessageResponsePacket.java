package com.javamain.im.singlechat.protocol.response;

import com.javamain.im.singlechat.protocol.Packet;
import lombok.Data;

import static com.javamain.im.simple01.protocol.command.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}