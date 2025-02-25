package com.javamain.im.pipelineandchannelhandlertest.protocol.response;

import com.javamain.im.pipelineandchannelhandlertest.protocol.Packet;
import lombok.Data;

import static com.javamain.im.simple01.protocol.command.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}
