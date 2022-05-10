package com.javamain.featuredemos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yzhou
 * @date 2022/5/5
 */
@Component
public class FlinkJobConfig {

    @Value("{flink.job.name}")
    public String name;
}
