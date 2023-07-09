package com.javamain.demo.nio.simpleim.my;


import com.javamain.demo.nio.simpleim.my.server.DiscardServer;

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
