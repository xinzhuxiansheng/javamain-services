package com.javamain.redis;

import java.io.IOException;
import java.net.Socket;

public class SocketClient {
    private Socket socket;
    public SocketClient(){
        try {
            socket = new Socket("172.168.175.129",6379);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Redis 连接失败");
        }
    }

    /**
     * 执行 Redis中的set命令 [set,key,value]
     */
//    public String set(String key, String value){
//        StringBuffer sb = new StringBuffer();
//        sb.append("3")
//                .append("\r\n")
//                .append()
//    }
}
