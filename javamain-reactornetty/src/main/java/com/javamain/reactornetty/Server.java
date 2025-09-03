package com.javamain.reactornetty;

import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerResponse;

import java.util.Arrays;
import java.util.List;

public class Server {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static void main(String[] args) {
    HttpServer.create()
            .host("0.0.0.0")
            .port(8082)
            .route(routes ->
                    routes
                            .get("/get/ids", (request, response) -> {
                              // 1. 读取请求体（假设不需要处理请求体，直接返回 IDs）
                              return request.receive()
                                      .then(createResponse(response));  // 直接传递 Mono<Void>
                            })
                            // 新增接口，获取 ID 并打印
                            .get("/get/id/{id}", (request, response) -> {
                              // 从 URL 获取 ID 参数
                              String id = request.param("id");
                              // 打印 id 到控制台
                              System.out.println("Received id: " + id);
                              // 返回 id 作为响应
                              return response.sendString(Mono.just("Received id: " + id));
                            })
            )
            .bindNow()
            .onDispose()
            .block();
  }

  private static Mono<Void> createResponse(HttpServerResponse response) {
    List<Integer> ids = Arrays.asList(
            21098365,
            21098366,
            21098367,
            21098368,
            21098369,
            21098371,
            21098372,
            21098373,
            21098374,
            21098375);
    try {
      String jsonResponse = objectMapper.writeValueAsString(ids);
      response.header("Content-Type", "application/json");
      return response.sendString(Mono.just(jsonResponse)).then();
    } catch (Exception e) {
      return Mono.error(e);
    }
  }
}
