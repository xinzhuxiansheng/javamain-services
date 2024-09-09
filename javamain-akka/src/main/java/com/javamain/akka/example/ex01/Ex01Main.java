package com.javamain.akka.example.ex01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yzhou
 * @date 2022/12/15
 */
public class Ex01Main {
    private static final Logger logger = LoggerFactory.getLogger(Ex01Main.class);

    public static void main(String[] args) throws InterruptedException {
        RequestData requestData = new RequestData();
        Thread.sleep(100000);
        logger.info("main end");
    }
}
