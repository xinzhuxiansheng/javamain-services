package com.javamain.springkafka.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServerRequest;

@Component
public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    public void startHttpServer() {
        logger.info("Http Server Starting ...");
        DisposableServer server = reactor.netty.http.server.HttpServer.create()
                .route(routes ->
                        routes.get("/hello",
                                        (request, response) ->
                                                response.sendString(
                                                        Mono.just("Hello World!")
                                                ))
                                .get("/list/{listenerId}",
                                        (request, response) -> response.sendString(
                                                Mono.just(has(request))
                                        ))
                                .post("/stop/{listenerId}",
                                        (request, response) -> response.sendString(
                                                Mono.just(stop(request))))
                                .post("/start/{listenerId}",
                                        (request, response) -> response.sendString(
                                                Mono.just(start(request))))
                )
                .host("localhost")
                .port(8080)
                .bindNow();
        server.onDispose();
    }

    private String has(HttpServerRequest request) {
        String listenerId = request.param("listenerId");
        kafkaListenerEndpointRegistry.getListenerContainer(listenerId).start();
        return "true";
    }

    private String start(HttpServerRequest request) {
        String listenerId = request.param("listenerId");
        kafkaListenerEndpointRegistry.getListenerContainer(listenerId).start();
        return "stop";
    }

    private String stop(HttpServerRequest request) {
        String listenerId = request.param("listenerId");
        kafkaListenerEndpointRegistry.getListenerContainer(listenerId).stop();
        return "stop";
    }
}