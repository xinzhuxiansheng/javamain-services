package com.javamain.netty.example01.chapter02;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        new Thread(() -> {
            while (true) {
                try {
                    // 1. 阻塞方法获取新连接
                    Socket socket = serverSocket.accept();

                    // 2. 为每一个连接都创建一个线程来读取数据
                    new Thread(() -> {
                        int len;
                        byte[] data = new byte[1024];
                        try {
                            InputStream inputStream = socket.getInputStream();
                            // 3. 按字节流方式读取数据
                            while ((len = inputStream.read(data)) != -1) {
                                System.out.println(new String(data, 0, len));
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
