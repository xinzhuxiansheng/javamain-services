package com.javamain.java8.websocket.simple;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;

public class MyWebSocketServer extends WebSocketServer {

    public MyWebSocketServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("欢迎使用我们的 WebSocket 服务器！"); //向新连接的客户端发送消息
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(conn + " 已经断开连接");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("收到： " + message);
        conn.send("已收到消息"); //可以回复一个消息
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
    }

    public static void main(String[] args) {
        MyWebSocketServer server = new MyWebSocketServer(new InetSocketAddress("localhost", 8887));
        server.start();
    }
}
