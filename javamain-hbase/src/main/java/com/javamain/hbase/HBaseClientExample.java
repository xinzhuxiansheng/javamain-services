package com.javamain.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseClientExample {
    public static void main(String[] args) {
        // 创建配置对象
        Configuration config = HBaseConfiguration.create();

        // 配置 Zookeeper 连接信息
        config.set("hbase.zookeeper.quorum", "bigdata01"); // 替换成你的 Zookeeper 地址
        config.set("hbase.zookeeper.property.clientPort", "2181"); // 替换成你的 Zookeeper 端口

        try (Connection connection = ConnectionFactory.createConnection(config)) {
            // 指定表名
            TableName tableName = TableName.valueOf("yzhou:person"); // 替换为你的表名
            Table table = connection.getTable(tableName);

            // 创建一个 Get 对象
            Get get = new Get(Bytes.toBytes("row1")); // 替换为你的行键
            Result result = table.get(get);

            // 从 Result 中获取数据
            byte[] value = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("name")); // 替换列族和列限定符
            System.out.println("Value: " + Bytes.toString(value));

            // 关闭表对象
            table.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
