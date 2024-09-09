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
public class AutoMysqlConfig {

    public String url;
    public String username;
    public String password;

    public Connection initConn() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
