package com.javamain.nettyinaction.chapter01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class BlockingIoClient {

    public void connectToServer(String host, int port) throws IOException {
        // 创建与服务器的连接
        Socket socket = new Socket(host, port);

        // 获取输出流，用于向服务器发送请求
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

        // 获取输入流，用于接收服务器响应
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // 向服务器发送请求
        out.println("Hello, Server!");

        // 获取并打印服务器的响应
        String response = in.readLine();
        System.out.println("Server Response: " + response);

        // 向服务器发送 "Done" 告知服务器结束
        out.println("Done");

        // 关闭连接
        socket.close();
    }

    public static void main(String[] args) {
        try {
            // 创建客户端实例并连接到服务端
            BlockingIoClient client = new BlockingIoClient();
            client.connectToServer("localhost", 8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
