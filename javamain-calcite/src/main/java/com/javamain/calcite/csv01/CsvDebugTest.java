package com.javamain.calcite.csv01;

import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class CsvDebugTest {
    
    public static void main(String[] args) throws SQLException {
        // 调试路径和文件
        String csvDirectory = "src/main/resources/csv";
        System.out.println("CSV Directory: " + csvDirectory);
        
        File dir = new File(csvDirectory);
        System.out.println("Directory exists: " + dir.exists());
        System.out.println("Is directory: " + dir.isDirectory());
        System.out.println("Absolute path: " + dir.getAbsolutePath());
        
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((file, name) -> name.toLowerCase().endsWith(".csv"));
            System.out.println("CSV files found: " + (files != null ? files.length : 0));
            if (files != null) {
                for (File file : files) {
                    String tableName = file.getName().substring(0, file.getName().length() - 4);
                    System.out.println("File: " + file.getName() + " -> Table: " + tableName.toUpperCase());
                }
            }
        }
        
        // 测试 Schema 创建
        CsvSchema csvSchema = new CsvSchema(csvDirectory);
        System.out.println("Tables in schema: " + csvSchema.getTableNames());
        
        // 创建 Calcite 连接并测试
        Properties info = new Properties();
        info.setProperty("lex", "JAVA");
        
        Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        rootSchema.add("CSV", csvSchema);
        
        System.out.println("Root schema sub-schemas: " + rootSchema.getSubSchemaNames());
        System.out.println("CSV schema tables: " + rootSchema.getSubSchema("CSV").getTableNames());
        
        connection.close();
    }
}