package com.javamain.calcite.csv01;

import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

public class CsvSchema extends AbstractSchema {
    
    private final String directory;
    private Map<String, Table> tableMap;

    public CsvSchema(String directory) {
        this.directory = directory;
    }

    @Override
    protected Map<String, Table> getTableMap() {
        if (tableMap == null) {
            tableMap = createTableMap();
        }
        return tableMap;
    }

    private Map<String, Table> createTableMap() {
        Map<String, Table> tables = new HashMap<>();
        
        File dir;
        // 如果是相对路径，尝试从类路径加载
        if (!directory.startsWith("/") && !directory.contains(":")) {
            // 类路径资源
            String resourcePath = "/" + directory.replace("src/main/resources/", "");
            java.net.URL url = getClass().getResource(resourcePath);
            if (url != null) {
                dir = new File(url.getFile());
            } else {
                // 回退到相对路径
                dir = new File(directory);
            }
        } else {
            // 绝对路径
            dir = new File(directory);
        }
        
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("CSV directory not found: " + dir.getAbsolutePath());
            return tables;
        }
        
        File[] files = dir.listFiles((file, name) -> name.toLowerCase().endsWith(".csv"));
        if (files != null) {
            for (File file : files) {
                String tableName = file.getName().substring(0, file.getName().length() - 4);
                tables.put(tableName.toUpperCase(), new CsvTable(file));
                System.out.println("Registered table: " + tableName.toUpperCase() + " from file: " + file.getName());
            }
        }
        
        return tables;
    }
}