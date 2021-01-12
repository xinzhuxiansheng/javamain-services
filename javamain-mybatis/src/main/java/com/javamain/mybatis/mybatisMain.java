package com.javamain.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class mybatisMain {
    public static void main(String[] args) throws IOException {

        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        String environment = "development";
        Properties mybatisConfig = new Properties();
        mybatisConfig.put("driver","com.mysql.jdbc.Driver");
        mybatisConfig.put("url","jdbc:mysql://127.0.0.1:3306/yzhoutest?characterEncoding=utf-8&autoReconnect=true");
        mybatisConfig.put("username","root");
        mybatisConfig.put("password","123456");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream,environment,mybatisConfig);



    }
}
