package com.yzhou.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author yzhou
 * @date 2022/8/9
 */
public class ModuleA {
    private static Logger logger = LogManager.getLogger();

    public static void main(String [] args){
        logger.debug("debug");
        logger.error("error");
        logger.fatal("fatal");
        logger.info("info");
        logger.trace("trace");
        logger.warn("warn");
    }
}