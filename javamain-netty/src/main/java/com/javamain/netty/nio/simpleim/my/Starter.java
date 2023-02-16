package com.javamain.netty.nio.simpleim.my;

import netty.my.server.DiscardServer;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
public class Starter {

    public static void main(String[] args) throws Exception {
        new DiscardServer(9001).run();
    }
}
