package com.javamain.netty.nio.base;

import com.javamain.netty.nio.base.server.DiscardServer;

public class Starter {
    public static void main(String[] args) throws Exception {
        new DiscardServer(9001).run();
    }
}
