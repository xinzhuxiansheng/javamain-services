package com.javamain.hutool.db;

import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * @author yzhou
 * @date 2022/10/26
 */
public class SqlExecutorTest {
    private static final Logger logger = LoggerFactory.getLogger(SqlExecutorTest.class);

    public static void main(String[] args) {
        test01("yzhou_tb_student");
    }

    public static Connection createMysqlConn(String url, String userName, String password) throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

    public static void test01(String TABLE_NAME) {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://test.mysql.com:3306/db_test?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "db_test_rw";
        String password = "DB#Test220307r$w";
            conn = createMysqlConn(url, username, password);
//            // 执行非查询语句，返回影响的行数
//            int count = SqlExecutor.execute(conn, "UPDATE " + TABLE_NAME + " set field1 = ? where id = ?", 0, 0);
//            log.info("影响行数：{}", count);
//            // 执行非查询语句，返回自增的键，如果有多个自增键，只返回第一个
//            Long generatedKey = SqlExecutor.executeForGeneratedKey(conn, "UPDATE " + TABLE_NAME + " set field1 = ? where id = ?", 0, 0);
//            log.info("主键：{}", generatedKey);

            /* 执行查询语句，返回实体列表，一个Entity对象表示一行的数据，Entity对象是一个继承自HashMap的对象，存储的key为字段名，value为字段值 */
            List<Entity> entityList = SqlExecutor.query(conn, "select * from " + TABLE_NAME
                    , new EntityListHandler());
//            entityList.forEach(entity -> {entity.})
            logger.info("{}", entityList);
        } catch (SQLException e) {
            // logger.error(log, e, "SQL error!");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
