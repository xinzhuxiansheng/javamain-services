package com.javamain.flash.netty.chapter07;

public interface Serializer {
    // JSON 序列化
    byte JSON_SERIALIZER = 1;
    Serializer DEFAULT = new JSONSerializer();

    // 序列化算法
    byte getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
