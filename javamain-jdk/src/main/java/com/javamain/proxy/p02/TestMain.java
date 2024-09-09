package com.javamain.proxy.p02;

import java.lang.reflect.Proxy;

public class TestMain {
    public static void main(String[] args) {

        Moveable proxy = (Moveable) Proxy.newProxyInstance(Moveable.class.getClassLoader(),
                new Class[]{Moveable.class},
                new TimeHandler());
        proxy.move();
    }
}
