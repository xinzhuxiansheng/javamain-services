package com.javamain.java8.websocket.simple;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class MyWebSocketClient extends WebSocketClient {

    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("你好，服务器！");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("收到： " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("连接已关闭： " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static void main(String[] args) throws URISyntaxException {
        URI serverUri = new URI("ws://localhost:16232");
        MyWebSocketClient client = new MyWebSocketClient(serverUri);
        client.connect();
    }
}
