package com.javamain.netty.protocol.client;

import java.util.concurrent.TimeUnit;

public class ClientStartUp {

    private static NettyClient nettyClient;

    public static void main(String[] args) throws InterruptedException {
        nettyClient = new NettyClient();
        nettyClient.initConnection();
        // 注册
        nettyClient.doRegistry();

        TimeUnit.SECONDS.sleep(1000);
        System.out.println("结束");
    }
}
