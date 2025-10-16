package com.javamain.calcite.csv01;

import com.javamain.calcite.udf.ExtendedSqlOperatorTable;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlOperatorTable;
import org.apache.calcite.sql.util.SqlOperatorTables;

import java.sql.*;
import java.util.Properties;

public class CsvQueryTest {
    
    public static void main(String[] args) throws SQLException {
        // 创建 Calcite 连接
        Properties info = new Properties();
        info.setProperty("lex", "JAVA");
        
        Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);

        // 创建链式操作符表，包含标准操作符和自定义操作符
        SqlOperatorTable operatorTable = SqlOperatorTables.chain(
                ExtendedSqlOperatorTable.instance()
        );

        // 获取根 Schema
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        
        // 添加 CSV Schema
        String csvDirectory = "src/main/resources/csv";
        CsvSchema csvSchema = new CsvSchema(csvDirectory);
        rootSchema.add("CSV", csvSchema);
        
        // 执行查询
        Statement statement = connection.createStatement();
        
        System.out.println("=== 查询员工表 ===");
        executeQuery(statement, "SELECT id,email FROM CSV.EMPLOYEES");
        
        System.out.println("\n=== 测试 Base64 Decode UDF ===");
        executeQuery(statement, "SELECT base64decode('SGVsbG8gV29ybGQ=') as decoded FROM CSV.EMPLOYEES");
        
        // 如果有 store_and_fwd_flag 列的话，可以这样调用
        // executeQuery(statement, "SELECT base64decode(store_and_fwd_flag, 2) FROM your_table");
        
//        System.out.println("\n=== 测试 Base64 UDF ===");
//        executeQuery(statement, "SELECT base64encode(CAST('Hello World' AS VARBINARY)) as encoded");
//        executeQuery(statement, "SELECT CAST(base64decode('SGVsbG8gV29ybGQ=') AS VARCHAR) as decoded");
//
//        System.out.println("\n=== 结合 CSV 数据使用 Base64 UDF ===");
//        executeQuery(statement, "SELECT name, base64encode(CAST(email AS VARBINARY)) as encoded_email FROM CSV.EMPLOYEES LIMIT 2");

//        System.out.println("\n=== 查询产品表 ===");
//        executeQuery(statement, "SELECT * FROM CSV.PRODUCTS");
//
//        System.out.println("\n=== 条件查询 - 年龄大于30的员工 ===");
//        executeQuery(statement, "SELECT name, age, city FROM CSV.EMPLOYEES WHERE age > '30'");
//
//        System.out.println("\n=== 连接查询 - 员工数量和产品数量 ===");
//        executeQuery(statement, "SELECT 'Employees' as type, COUNT(*) as count FROM CSV.EMPLOYEES " +
//                              "UNION ALL " +
//                              "SELECT 'Products' as type, COUNT(*) as count FROM CSV.PRODUCTS");
        
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