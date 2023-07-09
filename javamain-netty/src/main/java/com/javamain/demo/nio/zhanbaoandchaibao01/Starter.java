package com.javamain.demo.nio.zhanbaoandchaibao01;


import com.javamain.demo.nio.zhanbaoandchaibao01.server.DiscardServer;

/**
 * 验证粘包和拆包
 */
public class Starter {
    public static void main(String[] args) throws Exception {
        new DiscardServer(9001).run();
    }
}
