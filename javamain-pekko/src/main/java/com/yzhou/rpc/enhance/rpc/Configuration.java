package com.yzhou.rpc.enhance.rpc;

import java.util.Properties;

public class Configuration {
    Properties properties;

    public Configuration() {
        properties = new Properties();
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}
