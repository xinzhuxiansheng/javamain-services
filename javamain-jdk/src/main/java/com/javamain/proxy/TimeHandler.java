package com.javamain.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TimeHandler implements InvocationHandler {
    public TimeHandler(Object target) {
        super();
        this.target = target;
    }

    private Object target;


    /*
        InvocationHandler 的 invoke 方法在代理对象上的每个方法调用时都会被执行。通常，invoke 方法执行多次的原因是在使用代理对象时调用了多个方法。
        此外，一些方法，例如 equals、hashCode 和 toString，可能会在不经意间被调用。这可能是因为某些内部操作，例如在调试过程中 IDE 可能会调用 toString
        方法以显示对象的表示。这可能导致在调试过程中看到多次调用 invoke 方法。
        要解决这个问题，您可以在 InvocationHandler 的 invoke 方法中过滤掉不需要代理的方法
     */

    /*
     * 参数：
     * proxy  被代理对象
     * method  被代理对象的方法
     * args 方法的参数
     *
     * 返回值：
     * Object  方法的返回值
     * */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

        // 过滤掉不需要代理的方法，例如 equals、hashCode 和 toString
        if (method.getName().equals("equals") ||
                method.getName().equals("hashCode") ||
                method.getName().equals("toString")) {
            return method.invoke(target, args);
        }

        long starttime = System.currentTimeMillis();
        System.out.println("汽车开始行驶....");
        method.invoke(target);
        long endtime = System.currentTimeMillis();
        System.out.println("汽车结束行驶....  汽车行驶时间："
                + (endtime - starttime) + "毫秒！");
        return null;
    }
}
