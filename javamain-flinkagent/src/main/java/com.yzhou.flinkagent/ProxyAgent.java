package com.yzhou.flinkagent;


import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

public class ProxyAgent {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyAgent.class);

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        LOGGER.info("Starting flink porxy agent.");
        new AgentBuilder.Default()
                // 监听OutputFormat这个接口类的下面所有子类
                .type(ElementMatchers.hasSuperType(ElementMatchers.named("org.apache.flink.api.common.io.OutputFormat")))
                .transform((
                        builder, type, classLoader, module) ->
                        // 监听OutputFormat这个接口类的下面所有子类的writeRecord方法
                        builder.method(ElementMatchers.named("writeRecord"))
                                .intercept(MethodDelegation.to(WriteRecordInterceptor.class)))
                .installOn(instrumentation);
        LOGGER.info("Flink porxy agent started.");
    }
}
