package com.javamain.kafkaClient.reflect;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestMain {

    public static void main(String[] args) throws Exception {

        String configFileName = "config.properties";
        Properties pros = PropertiesUtil.loadProperties(configFileName);
        String classPackPath = pros.getProperty("interceptor.classes");
        Object o = newInstance(loadClass(classPackPath, ProducerInterceptor.class));
        List<ProducerInterceptor> interceptorList = new ArrayList<>();
        interceptorList.add(ProducerInterceptor.class.cast(o));

        System.out.println(doSend(interceptorList, "第一条消息"));
    }

    public static String doSend(List<ProducerInterceptor> interceptorList, String message) {
        String interceptMessage = message;
        for (ProducerInterceptor interceptor : interceptorList) {
            interceptMessage = interceptor.onSend(message);
        }
        return interceptMessage;
    }


    public static <T> Class<? extends T> loadClass(String klass, Class<T> base) throws ClassNotFoundException {
        return Class.forName(klass, true, getClassContext()).asSubclass(base);
    }

    private static ClassLoader getClassContext() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null)
            return TestMain.class.getClassLoader();
        else
            return cl;
    }

    public static <T> T newInstance(Class<T> c) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (c == null)
            throw new NullPointerException("class cannot be null");
        return c.getDeclaredConstructor().newInstance();
    }

}
