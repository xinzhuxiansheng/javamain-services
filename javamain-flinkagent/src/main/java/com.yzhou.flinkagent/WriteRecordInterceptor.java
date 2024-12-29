package com.yzhou.flinkagent;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 拦截器
 *
 */
public class WriteRecordInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyAgent.class);

    @RuntimeType
    public static Object intercept(@Origin Method method, @AllArguments Object[] args, @SuperCall Callable<?> callable) throws Exception {
        String clazzName = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        LOGGER.info("Ready to {}#{} content. ", clazzName, methodName);
        try {
            // 获取方法的参数类型
            Class<?>[] parameterTypes = method.getParameterTypes();
            // 打印方法参数的名称和内容(这里可以发送到kafka等操作)
            for (int i = 0; i < parameterTypes.length; i++) {
                LOGGER.info("Parameter {}: {} = {}", i, parameterTypes[i].getName(), args[i]);
            }
            return callable.call();
        } catch (Exception e) {
            LOGGER.error("Record error. {}#{}", clazzName, methodName, e);
            throw e;
        }
    }
}
