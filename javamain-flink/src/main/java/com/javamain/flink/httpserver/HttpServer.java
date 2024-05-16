package com.javamain.flink.httpserver;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.server.Route;
import com.javamain.flink.httpserver.route.v0.JobRoutes;
import scala.concurrent.ExecutionContextExecutor;

public class HttpServer {
    public static void startHttpServer(ActorSystem system, ExecutionContextExecutor executor) {
        final Http http = Http.get(system);
        Route routes = new JobRoutes(system,executor).routes();
        http.newServerAt("localhost", 8080)
                .bind(routes)
                .thenAccept(binding -> {
                    System.out.println("HTTP Server started at http://0.0.0.0:8080/");
                });
    }
}
