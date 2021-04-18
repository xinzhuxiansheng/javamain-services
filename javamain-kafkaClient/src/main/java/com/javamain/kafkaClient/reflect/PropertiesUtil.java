package com.javamain.kafkaClient.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class PropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    public PropertiesUtil() {
    }

    public static Properties loadProperties(String propertiesName) {
        Properties pro = new Properties();
        InputStream in = null;

        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName);
            if (in != null) {
                pro.load(in);
            } else {
                in = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesName);
                if (in != null) {
                    Properties addProps = new Properties();
                    addProps.load(in);
                    Iterator var4 = addProps.keySet().iterator();

                    while(var4.hasNext()) {
                        Object o = var4.next();
                        pro.put(o.toString(), addProps.getProperty(o.toString()));
                    }
                }
            }
        } catch (Exception var6) {
            logger.error("PropertiesUtil_loadProperties：加载配置文件异常，{}", var6.getMessage());
        }

        return pro;
    }
}
