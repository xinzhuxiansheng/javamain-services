package com.javamain.kafkaJMX;

import javax.management.MBeanServerConnection;
import java.io.IOException;

public class test01 {

    public static void main(String[] args) throws IOException {

        JMXUtils jmxUtils = new JMXUtils();
        String url = jmxUtils.getUrl("xx.yy.100.17","9999");
        MBeanServerConnection connection =  jmxUtils.getMBeanServer(url);
        System.out.println("连接");
    }

}
