package com.javamain.antlr.util;

import com.javamain.antlr.csvsql.BooleanExpressionInfo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

/**
 * csv file 操作 utils
 */
public class CSVUtils {

    private static String folder = "/Users/a/TMP/sql/";

    private static Map<String, List<String>> allTables = new HashMap<>();

    static {
        allTables.put("product", Arrays.stream("id,name,desc,price".split(",")).collect(Collectors.toList()));
    }

    private static String getFileAbsolutePath(String fileName) {
        return folder + fileName;
    }

    /**
     * 创建文件
     *
     * @param fileName
     * @param headers
     */
    public static void createCSV(String fileName, String... headers) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(getFileAbsolutePath(fileName)));
             CSVPrinter csvPrinter = new CSVPrinter(writer,
                     CSVFormat.Builder.create()
                             .setHeader(headers)
                             .setRecordSeparator("\n")
                             .build())) {
            allTables.put(fileName, Arrays.stream(headers).collect(Collectors.toList()));
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insertData(String fileName, String... data) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(getFileAbsolutePath(fileName)),
                StandardOpenOption.APPEND);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            // 插入数据行
            csvPrinter.printRecord((Object[]) data);
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String fileName) throws IOException {
        Path path = Paths.get(getFileAbsolutePath(fileName));
        if (Files.exists(path)) {
            Files.delete(path);
            allTables.remove(fileName); // 移除缓存
        } else {
            System.out.println("File not found: " + fileName);
        }
    }

    public static List<Map<String, Object>> readFromCsv(String fileName, List<String> selectedFields, BooleanExpressionInfo whereClause) {
        List<Map<String, Object>> rows = selectAllData(fileName, selectedFields);
        return rows.stream()
                .filter(row -> satisfiesWhereClause(row, whereClause))
                //.map(row -> selectFields(row, selectedFields))
                .collect(Collectors.toList());
    }

    private static Map<String, Object> csvRecordToMap(String fileName, CSVRecord record) {
        Map<String, Object> recordMap = new HashMap<>();
        List<String> columns = allTables.get(fileName);
        for (String header : columns) {
            recordMap.put(header, record.get(header));
        }
        return recordMap;
    }

    private static boolean satisfiesWhereClause(Map<String, Object> row, BooleanExpressionInfo whereClause) {
        if (whereClause == null) {
            return true; // 没有 WHERE 子句时，始终返回 true
        }

        if (whereClause.getOperator() != null) {
            switch (whereClause.getOperator()) {
                case "=":
                    return Objects.equals(row.get(whereClause.getLeftOperand()), parseValue(whereClause.getRightOperand()));
                case ">":
                    return compare(row.get(whereClause.getLeftOperand()), whereClause.getRightOperand()) > 0;
                case "<":
                    return compare(row.get(whereClause.getLeftOperand()), parseValue(whereClause.getRightOperand())) < 0;
                // 添加其他比较操作符的处理逻辑
            }
        }

        if (whereClause.getLeft() != null && whereClause.getRight() != null) {
            switch (whereClause.getOperator()) {
                case "AND":
                    return satisfiesWhereClause(row, whereClause.getLeft()) && satisfiesWhereClause(row, whereClause.getRight());
                case "OR":
                    return satisfiesWhereClause(row, whereClause.getLeft()) || satisfiesWhereClause(row, whereClause.getRight());
            }
        }

        return false;
    }

    private static Object parseValue(String value) {
        if (value == null) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // 值不是整数，保留为字符串
            return value;
        }
    }

    private static int compare(Object obj1, Object obj2) {
        if (obj1 == null || obj2 == null) {
            return 0;
        }

        if (obj1 instanceof Comparable && obj1.getClass().equals(obj2.getClass())) {
            return ((Comparable) obj1).compareTo(obj2);
        }

        return 0; // 如果类型不匹配或者不可比较，则返回 0
    }

    public static List<Map<String, Object>> selectAllData(String fileName, List<String> selectedFields) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(getFileAbsolutePath(fileName)));
             CSVParser csvParser = new CSVParser(reader,
                     CSVFormat.Builder.create()
                             .setHeader(allTables.get(fileName).toArray(new String[0]))
                             .setSkipHeaderRecord(true)
                             .setTrim(true).build())) {
            List<CSVRecord> records = csvParser.getRecords();
            List<Map<String, Object>> result = new ArrayList<>();
            Map<String, Object> itemM = null;

            boolean isAll = false;
            if (selectedFields.size() == 1 && selectedFields.get(0).equals("*")) {
                isAll = true;
            }

            for (CSVRecord record : records) {
                // select * ，无法放入 hash
                if (isAll) {
                    result.add(csvRecordToMap(fileName, record));
                    continue;
                }

                itemM = new HashMap<>();
                for (String c : selectedFields) {
                    itemM.put(c, record.get(c));
                }
                result.add(itemM);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        //createCSV("/Users/a/Code/Java/javamain-services/javamain-antlr/src/main/java/com/javamain/antlr/util/111.cvs", "id", "name");
//        insertData("/Users/a/Code/Java/javamain-services/javamain-antlr/src/main/java/com/javamain/antlr/util/111.cvs",
//                "1","yzhou");
    }

}
