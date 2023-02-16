package com.javamain.netty.nio.simpleim.chat;

import com.javamain.netty.nio.simpleim.base.server.DiscardServer;

public class Starter {

    // line
    // header
    // body

    public static void main(String[] args) throws Exception {
        new DiscardServer(9001).run();
    }
}
