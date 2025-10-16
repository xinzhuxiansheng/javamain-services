package com.javamain.calcite.csv01;

import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CsvSchemaFactoryDemo {
    
    public static void main(String[] args) throws SQLException {
        // 使用 Schema Factory 方式创建连接
        Properties info = new Properties();
        info.setProperty("lex", "JAVA");
        
        Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        
        // 获取根 Schema
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        
        // 使用 SchemaFactory 创建 CSV Schema
        Map<String, Object> operand = new HashMap<>();
        operand.put("directory", "src/main/resources/csv");
        
        CsvSchemaFactory factory = new CsvSchemaFactory();
        rootSchema.add("CSV_FACTORY", factory.create(rootSchema, "CSV_FACTORY", operand));
        
        // 执行查询测试
        Statement statement = connection.createStatement();
        
        System.out.println("=== 使用 SchemaFactory 创建的 CSV Schema 查询 ===");
        executeQuery(statement, "SELECT name, email, city FROM CSV_FACTORY.EMPLOYEES WHERE age >= '30' ORDER BY age");
        
        System.out.println("=== 产品价格统计 ===");
        executeQuery(statement, "SELECT category, COUNT(*) as product_count, AVG(CAST(price as DOUBLE)) as avg_price " +
                              "FROM CSV_FACTORY.PRODUCTS GROUP BY category");
        
        // 关闭连接
        statement.close();
        connection.close();
    }
    
    private static void executeQuery(Statement statement, String sql) throws SQLException {
        System.out.println("SQL: " + sql);
        ResultSet resultSet = statement.executeQuery(sql);
        
        // 输出列名
        int columnCount = resultSet.getMetaData().getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(resultSet.getMetaData().getColumnName(i));
            if (i < columnCount) System.out.print("\t");
        }
        System.out.println();
        
        // 输出数据
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i));
                if (i < columnCount) System.out.print("\t");
            }
            System.out.println();
        }
        
        resultSet.close();
        System.out.println();
    }
}