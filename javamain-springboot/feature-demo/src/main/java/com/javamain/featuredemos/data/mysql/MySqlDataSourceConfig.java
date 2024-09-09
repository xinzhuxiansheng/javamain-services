package com.javamain.featuredemos.data.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.time.Duration;

@Configuration
public class MySqlDataSourceConfig {
    @Bean
    //@ConfigurationProperties("app.datasource.custom")
    public HikariConfig hikariConfig() {
        return createHikariConfig(
                "jdbc:mysql://localhost:3306/yzhou_test",
                false,
                1,
                "root",
                "12345678"
        );
    }

    private HikariConfig createHikariConfig(String url, boolean cluster, int index,String user,String pass) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(pass);

        if (cluster) {
            hikariConfig.setConnectionInitSql("SET @@SESSION.group_replication_consistency=EVENTUAL;");
        }
        hikariConfig.addDataSourceProperty("useSSL", false);
        hikariConfig.addDataSourceProperty("requireSSL", false);
        hikariConfig.addDataSourceProperty("verifyServerCertificate", false);
        hikariConfig.addDataSourceProperty("useInformationSchema", true);
        hikariConfig.addDataSourceProperty("useUnicode", true);
        hikariConfig.addDataSourceProperty("characterEncoding", "utf8");
        hikariConfig.addDataSourceProperty("allowPublicKeyRetrieval", true);
        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("cacheCallableStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 444);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        hikariConfig.addDataSourceProperty("alwaysSendSetIsolation", false);
        hikariConfig.addDataSourceProperty("useLocalSessionState", true);
        hikariConfig.addDataSourceProperty("useLocalTransactionState", true);
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", true);
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", true);
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", true);
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", true);
        hikariConfig.addDataSourceProperty("maintainTimeStats", false);
        hikariConfig.addDataSourceProperty("allowMultiQueries", true);
        hikariConfig.addDataSourceProperty("autoReconnect", true);
        hikariConfig.addDataSourceProperty("autoReconnectForPools", true);
        hikariConfig.addDataSourceProperty("failOverReadOnly", false);
        hikariConfig.addDataSourceProperty("maxReconnects", 3);
        hikariConfig.addDataSourceProperty("initialTimeout", 5);
        hikariConfig.setPoolName("coordinator-hikari-pool-" + 1);
        hikariConfig.setRegisterMbeans(true);
        hikariConfig.setMaxLifetime(Duration.ofHours(7L).toMillis());
        hikariConfig.setMaximumPoolSize(100);
        hikariConfig.setMinimumIdle(10);
        hikariConfig.setIdleTimeout(Duration.ofMinutes(5L).toMillis());
        hikariConfig.setConnectionTimeout(Duration.ofSeconds(15L).toMillis());
        hikariConfig.setValidationTimeout(Duration.ofMillis(300L).toMillis());
        hikariConfig.setLeakDetectionThreshold(Duration.ofSeconds(10L).toMillis());

        return hikariConfig;
    }


    @Bean
    public DataSource dataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }
}
