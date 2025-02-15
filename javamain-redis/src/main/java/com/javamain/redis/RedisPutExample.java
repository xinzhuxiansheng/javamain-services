package com.javamain.redis;

import redis.clients.jedis.Jedis;

public class RedisPutExample {

    public static void main(String[] args) {
        // 创建一个 Jedis 客户端，连接到本地 Redis 服务
        Jedis jedis = new Jedis("localhost", 6379);

        // 可以设置一个密码（如果 Redis 配置了密码的话）
        // jedis.auth("your_password");

        // 检查连接是否成功
        if (jedis.ping().equals("PONG")) {
            System.out.println("Connected to Redis server successfully");
        }

        for (int i = 0; i < 1; i++) {
            jedis.set("key-" + i,"new-value-"+i);
        }

        System.out.println("put 结束");

//        // 使用 Redis 的 PUT 操作，向 Redis 中添加一个键值对
//        String key = "name";
//        String value = "Alice";
//
//        // 将键值对放入 Redis
//        jedis.set(key, value);
//
//        // 获取并打印存入的值
//        String retrievedValue = jedis.get("key-1");
//        System.out.println(retrievedValue);
//        jedis.getDel("key-1");
//        System.out.println("The value for the key '" + key + "' is: " + retrievedValue);

        // 关闭连接
        jedis.close();
    }
}
