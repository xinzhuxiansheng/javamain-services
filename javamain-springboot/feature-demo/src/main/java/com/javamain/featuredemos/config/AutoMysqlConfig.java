package com.javamain.featuredemos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author yzhou
 * @date 2022/5/5
 */
@Component
@ConfigurationProperties(prefix = "mysql2")
@Data
public class AutoMysqlConfig {

    public String url;
    public String username;
    public String password;

    public Connection initConn() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
