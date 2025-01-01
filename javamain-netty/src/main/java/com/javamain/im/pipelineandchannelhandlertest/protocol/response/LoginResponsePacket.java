package com.javamain.im.pipelineandchannelhandlertest.protocol.response;

import com.javamain.im.pipelineandchannelhandlertest.protocol.Packet;
import lombok.Data;

import static com.javamain.im.simple01.protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
