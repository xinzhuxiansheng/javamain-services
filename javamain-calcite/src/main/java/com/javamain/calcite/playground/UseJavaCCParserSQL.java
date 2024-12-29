package com.javamain.calcite.playground;

import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

public class UseJavaCCParserSQL {
    public static void main(String[] args) throws SqlParseException {
        System.out.println("\r\n... mysqlParser ...");
        mysqlParser();

        System.out.println("\r\n... ksqlParser ...");
        ksqlParser();

    }

    private static void mysqlParser() throws SqlParseException {
        // SQL语句
        String sql = "select * from t_user where id = 1 limit 10";
        // 解析配置SqlParse
        SqlParser.Config mysqlConfig = SqlParser.config().withLex(Lex.MYSQL);

        // 创建解析器
        SqlParser parser = SqlParser.create(sql, mysqlConfig);

        // 解析SQL语句
        SqlNode sqlNode = parser.parseQuery();
        System.out.println(sqlNode.toString());
    }

    private static void ksqlParser() throws SqlParseException {
        // SQL语句
        String sql = "select * from yzhoutpjson01 where `partition` in (0) limit 10";
        // 解析配置SqlParse
        SqlParser.Config mysqlConfig = SqlParser.config().withLex(Lex.JAVA);

        // 创建解析器
        SqlParser parser = SqlParser.create(sql, mysqlConfig);

        // 解析SQL语句
        SqlNode sqlNode = parser.parseQuery();
        System.out.println(sqlNode.toString());
    }
}
