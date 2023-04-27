package com.javamain.proxy.p02;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TimeHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

        // 过滤掉不需要代理的方法，例如 equals、hashCode 和 toString
        if (method.getName().equals("equals") ||
                method.getName().equals("hashCode") ||
                method.getName().equals("toString")) {
            return "";
        }

        long starttime = System.currentTimeMillis();
        System.out.println("汽车开始行驶....");
        // method.invoke();
        long endtime = System.currentTimeMillis();
        System.out.println("汽车结束行驶....  汽车行驶时间："
                + (endtime - starttime) + "毫秒！");
        return null;
    }
}
