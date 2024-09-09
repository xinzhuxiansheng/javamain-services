package com.yzhou.log4j.web;

public class Bootstrap {
    public static void main(String[] args) throws Exception {
        JettyServer jettyServer = new JettyServer();
        jettyServer.start();
    }
}
