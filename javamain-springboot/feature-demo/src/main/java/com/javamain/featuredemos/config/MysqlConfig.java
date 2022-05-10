package com.javamain.featuredemos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author yzhou
 * @date 2022/5/5
 */
@Component
public class MysqlConfig {

    @Value("${mysql.url}")
    private String url;
    @Value("${mysql.username}")
    private String username;
    @Value("${mysql.password: default-pwd-value }")
    private String password;

    public Connection initConn() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public String getPassword(){
        return password;
    }
}
