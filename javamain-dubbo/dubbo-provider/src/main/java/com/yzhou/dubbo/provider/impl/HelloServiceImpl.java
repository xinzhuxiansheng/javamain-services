package com.yzhou.dubbo.provider.impl;

import com.yzhou.javaproject.dubbo.service.HelloServiceAPI;

public class HelloServiceImpl implements HelloServiceAPI {
    @Override
    public String sayHello(String message) {
        return "Producer reponse: Hello " + message;
    }
}
