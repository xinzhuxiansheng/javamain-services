package com.yzhou.demo.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yzhou
 * @date 2022/7/28
 */
public class Log2KafkaProducer {
    private static Logger logger = LoggerFactory.getLogger(Log2KafkaProducer.class);
    public static void send(String msg){
        logger.info(msg);
    }
}
