package com.javamain.nettyinaction.chapter01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockingIoExample {

    public void serve(int portNumber) throws Exception {
        // 创建服务器端的 ServerSocket
        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("Server is listening on port " + portNumber);

        // 接受客户端连接
        Socket clientSocket = serverSocket.accept();
        System.out.println("New client connected: " + clientSocket.getInetAddress());

        // 获取输入输出流
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String message;
        while ((message = in.readLine()) != null) {
            if ("Done".equalsIgnoreCase(message)) {
                System.out.println("Server is closing the connection.");
                break;  // 如果接收到 "Done" 则退出循环并关闭连接
            }
            System.out.println("Received message: " + message);  // 打印收到的每一条消息
            out.println("Message received: " + message);  // 向客户端发送响应
        }

        // 关闭连接
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) {
        try {
            BlockingIoExample server = new BlockingIoExample();
            server.serve(8080);  // 监听端口8080
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void serve(int portNumber) throws Exception {
//        ServerSocket serverSocket = new ServerSocket(portNumber);
//        Socket clientSocket = serverSocket.accept();
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(clientSocket.getInputStream()));
//        PrintWriter out =
//                new PrintWriter(clientSocket.getOutputStream(), true);
//        String request, response;
//        while ((request = in.readLine()) != null) {
//            if ("Done".equals(request)) {
//                break;
//            }
//            response = processRequest(request);
//            out.println(response);
//        }
//    }
//
//    private String processRequest(String request){
//        return "Processed";
//    }
//
//    public static void main(String[] args) throws Exception {
//        BlockingIoExample example = new BlockingIoExample();
//        example.serve(8080);
//    }
}
