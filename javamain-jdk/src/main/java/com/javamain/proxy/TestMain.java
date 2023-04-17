package com.javamain.proxy;

import java.lang.reflect.Proxy;

public class TestMain {
    /**
     * JDK动态代理测试类
     */
    public static void main(String[] args) {
//        Car car = new Car();
//        InvocationHandler h = new TimeHandler(car);
//        Class<?> cls = car.getClass();
//        /**
//         * loader  类加载器
//         * interfaces  实现接口
//         * h InvocationHandler
//         */
//        Moveable m = (Moveable) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), h);
//        m.move();

        Moveable car = new Car();
        Moveable proxy = (Moveable) Proxy.newProxyInstance(car.getClass().getClassLoader(), car.getClass().getInterfaces(),
                new TimeHandler(car));
        proxy.move();
    }
}
