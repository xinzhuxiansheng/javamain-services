package com.javamain.schema.typeutils;

import com.javamain.schema.column.ConfluentAvroColumn02;
import com.javamain.schema.typeinfo.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SQLConvertUtils02 {


    public static String convertaaaa(ConfluentAvroColumn02 confluentAvroColumn02, Boolean isType) {
        SchemaTypeInfo schemaTypeInfo = confluentAvroColumn02.getSchemaTypeInfo();
        String name = confluentAvroColumn02.getName();

        // 等于基础类型
        if (BasicTypeInfo.isBasicTypeInfo(schemaTypeInfo)) {
            return schemaTypeInfo.toFlinkColumnString(confluentAvroColumn02.getName());
        }
        if (schemaTypeInfo instanceof RecordTypeInfo) {
            String start = String.format("%s ROW <", StringUtils.isBlank(name) ? "" :
                    isType ? "" : String.format("`%s`", name));
            List<String> schemaLines = new ArrayList<>();

            confluentAvroColumn02.getSubFields().forEach(c -> {
                schemaLines.add(convertaaaa(c, false));
            });
            String end = ">";
            return String.format("%s\n%s\n%s",
                    start,
                    schemaLines.stream()
                            .collect(Collectors.joining(",\n")),
                    end);
        }
        if (schemaTypeInfo instanceof MapTypeInfo) {
            String start = String.format("%s MAP < STRING, ", StringUtils.isBlank(name) ? "" :
                    isType ? "" : String.format("`%s`", name));
            String fieldsStr = convertaaaa(confluentAvroColumn02.getSubFields().get(0), true);
            String end = " >";
            return String.format("%s\n%s\n%s", start, fieldsStr, end);
        }
        if (schemaTypeInfo instanceof ArrayTypeInfo) {
            //return convert(name, ((ArrayTypeInfo) schemaTypeInfo).getElementType());
            String start = String.format("%s ARRAY <", StringUtils.isBlank(name) ? "" : String.format("`%s`", name));
            List<String> schemaLines = new ArrayList<>();
            confluentAvroColumn02.getSubFields().forEach(c -> {
                schemaLines.add(convertaaaa(c, true));
            });
            String end = ">";
            return String.format("%s\n%s\n%s",
                    start,
                    schemaLines.stream()
                            .collect(Collectors.joining(",\n")),
                    end);
        }
        if (schemaTypeInfo instanceof UnionTypeInfo) {
            // Union 会多出一个层级，因为 Union 是一个 由其他类型组合而成。
            // 必须注意，因为 是嵌套，但是子节点的name
            ConfluentAvroColumn02 subColumn = confluentAvroColumn02.getSubFields().get(0);
            subColumn.setName(name);
            return convertaaaa(subColumn, false);
        }
        return "";
    }


//    public static String convert(ConfluentAvroColumn02 confluentAvroColumn02) {
//        SchemaTypeInfo schemaTypeInfo = confluentAvroColumn02.getSchemaTypeInfo();
//        String name = confluentAvroColumn02.getName();
//
//        // 等于基础类型
//        if (BasicTypeInfo.isBasicTypeInfo(schemaTypeInfo)) {
//            return schemaTypeInfo.toFlinkColumnString(name);
//        }
//        if (schemaTypeInfo instanceof RecordTypeInfo) {
//            String start = String.format(" ROW < ");
//            List<String> schemaLines = new ArrayList<>();
//            confluentAvroColumn02.getSubFields().forEach(c -> {
//                schemaLines.add(convert(c));
//            });
//            String end = " > ";
//            return String.format("%s\n%s\n%s",
//                    start,
//                    schemaLines.stream()
//                            .collect(Collectors.joining(",\n")),
//                    end);
//        }
//        if (schemaTypeInfo instanceof MapTypeInfo) {
//            List<String> schemaLines = new ArrayList<>();
//            confluentAvroColumn02.getSubFields().forEach(c -> {
//                schemaLines.add(convert(c));
//            });
//            return schemaLines.stream()
//                    .collect(Collectors.joining(",\n"));
//        }
//        if (schemaTypeInfo instanceof ArrayTypeInfo) {
//            String start = String.format("%s ARRAY < ", StringUtils.isBlank(name) ? "" : String.format("`%s`", name));
//            List<String> schemaLines = new ArrayList<>();
//            confluentAvroColumn02.getSubFields().forEach(c -> {
//                schemaLines.add(convert(c));
//            });
//            String end = " > ";
//            return String.format("%s\n%s\n%s",
//                    start,
//                    schemaLines.stream()
//                            .collect(Collectors.joining(",\n")),
//                    end);
//        }
//        return "";
//    }
}
