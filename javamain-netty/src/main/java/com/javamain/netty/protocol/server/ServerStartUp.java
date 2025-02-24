package com.javamain.netty.protocol.server;

public class ServerStartUp {
    private static NettyServer nettyServer;

    public static void main(String[] args) throws InterruptedException {
        nettyServer = new NettyServer(9090);
        nettyServer.startServer();
    }
}
