package com.javamain.im.pipelineandchannelhandlertest.protocol.request;

import com.javamain.im.pipelineandchannelhandlertest.protocol.Packet;
import lombok.Data;

import static com.javamain.im.simple01.protocol.command.Command.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
