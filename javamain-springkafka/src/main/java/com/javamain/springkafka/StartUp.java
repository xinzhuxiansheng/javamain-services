package com.javamain.springkafka;

import com.javamain.springkafka.http.HttpServer;
import com.javamain.springkafka.kafka.KafkaConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class StartUp {
    private static final Logger logger = LoggerFactory.getLogger(StartUp.class);

    private static HttpServer httpServer;

    @Autowired
    public void setHttpServer(HttpServer httpServer) {
        StartUp.httpServer = httpServer;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.javamain.springkafka");
        context.refresh();

        httpServer.startHttpServer();

        CountDownLatch latch = new CountDownLatch(1);
        try {
            latch.await(); // 阻塞主线程
        } catch (InterruptedException e) {
            logger.error("Process interrupted", e);
            Thread.currentThread().interrupt();
        } finally {
            context.close();
            logger.info("Spring container closed.");
        }
    }
}
