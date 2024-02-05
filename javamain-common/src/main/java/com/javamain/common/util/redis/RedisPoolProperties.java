package com.javamain.common.util.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "spring.redis.lettuce.pool")
@Data
public class RedisPoolProperties {

    //最大空闲连接
    private Integer maxIdle;
    //最小空闲连接
    private Integer minIdle;
    //最大连接数
    private Integer maxActive;
    //最大建立连接等待时间
    private Duration maxWait;
}
