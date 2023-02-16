package com.javamain.netty.nio.simpleim.base;

import com.javamain.netty.nio.simpleim.base.server.DiscardServer;

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
