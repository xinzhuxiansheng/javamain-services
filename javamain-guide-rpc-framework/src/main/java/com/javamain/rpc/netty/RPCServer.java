package com.javamain.rpc.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class RPCServer {
    private static final Logger logger = LoggerFactory.getLogger(RPCServer.class);
    private static final int PORT = 9998;


    public void registerService(String zkServer, String serverName) {

    }

    public void start() {
        try {
            String host = InetAddress.getLocalHost().getHostAddress();

        } catch (UnknownHostException e) {
            logger.error("", e);
        }
    }
}
