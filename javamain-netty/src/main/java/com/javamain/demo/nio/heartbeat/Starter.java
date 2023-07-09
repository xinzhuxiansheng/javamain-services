package com.javamain.demo.nio.heartbeat;


import com.javamain.demo.nio.heartbeat.server.DiscardServer;

public class Starter {


    //2

    //

    //0-2 + 1 = 3 ___3 //5

    public static void main(String[] args) throws Exception {
        new DiscardServer(9001).run();
    }
}
