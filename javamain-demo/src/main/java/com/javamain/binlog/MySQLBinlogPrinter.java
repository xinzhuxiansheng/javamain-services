package com.javamain.binlog;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;

public class MySQLBinlogPrinter {
    public static void main(String[] args) {
        // MySQL Binlog 配置
        String hostname = "192.168.0.201";  // MySQL 地址
        int port = 3306;               // MySQL 端口
        String username = "root";      // MySQL 用户名
        String password = "123456";    // MySQL 密码

        // 创建 Binlog Client
        BinaryLogClient client = new BinaryLogClient(hostname, port, username, password);

        // 添加监听器，处理 Binlog 事件
        client.registerEventListener(event -> {
            // 打印事件类型和数据
            EventData data = event.getData();
            if (data != null) {
                System.out.println("Event Type: " + event.getHeader().getEventType());
                System.out.println("Event Data: " + data);
            }
        });

        try {
            // 启动 Binlog Client
            System.out.println("Starting MySQL Binlog Client...");
            client.connect();
        } catch (Exception e) {
            System.err.println("Error connecting to MySQL Binlog: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
