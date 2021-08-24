package com.javamain.java.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadLocalDebug {
    private static final Logger logger = LoggerFactory.getLogger(ThreadLocalDebug.class);

    public static void main(String[] args) {

        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        //threadLocal.set();
    }
}
