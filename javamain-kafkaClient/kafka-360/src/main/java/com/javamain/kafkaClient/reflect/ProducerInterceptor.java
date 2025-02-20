package com.javamain.kafkaClient.reflect;

public interface ProducerInterceptor {
    public String onSend(String record);
}
