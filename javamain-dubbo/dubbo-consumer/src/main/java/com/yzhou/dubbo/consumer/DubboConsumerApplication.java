package com.yzhou.dubbo.consumer;

import com.yzhou.javaproject.dubbo.service.HelloServiceAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:applicationContext-dubbo.xml"})
public class DubboConsumerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DubboConsumerApplication.class, args);

        HelloServiceAPI providerService = context.getBean("providerService", HelloServiceAPI.class);
        String message = "yzhou";
        System.out.println(providerService.sayHello(message));
    }
}
