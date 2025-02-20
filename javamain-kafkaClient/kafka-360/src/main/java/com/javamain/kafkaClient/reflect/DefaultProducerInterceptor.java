package com.javamain.kafkaClient.reflect;

public class DefaultProducerInterceptor implements ProducerInterceptor{
    private static final String testTag = ".testTag";

    @Override
    public String onSend(String record) {
        return record.concat(testTag);
    }
}
