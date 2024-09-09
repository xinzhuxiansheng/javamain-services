package com.javamain.schema.typeutils;

public class SQLConvertUtils {

//    public static String convertaaaa(String name, SchemaTypeInfo schemaTypeInfo) {
//        // 等于基础类型
//        if (BasicTypeInfo.isBasicTypeInfo(schemaTypeInfo)) {
//            return  schemaTypeInfo.toFlinkColumnString(name);
//        }
//        if (schemaTypeInfo instanceof RecordTypeInfo) {
//            String start = String.format("`%s` ROW <", name);
//            List<String> schemaLines = new ArrayList<>();
//            ((RecordTypeInfo) schemaTypeInfo).getFields().forEach((key, value) -> {
//                schemaLines.add(convert(key, value));
//            });
//            String end = ">";
//            return String.format("%s\n%s\n%s",
//                    start,
//                    schemaLines.stream()
//                            .collect(Collectors.joining(",\n")),
//                    end);
//        }
//        if (schemaTypeInfo instanceof MapTypeInfo) {
////            String start = String.format("`%s` MAP < STRING, ", name);
////            String fieldsStr = convert(name, ((MapTypeInfo) schemaTypeInfo).getValue());
////            String end = " >";
////            return String.format("%s\n%s\n%s", start, fieldsStr, end);
//            return schemaTypeInfo.toFlinkColumnString(name);
//        }
//        if (schemaTypeInfo instanceof ArrayTypeInfo) {
//            return convert(name, ((ArrayTypeInfo) schemaTypeInfo).getElementType());
//        }
//        return "";
//    }
//
//
//    public static String convert(String name, SchemaTypeInfo schemaTypeInfo) {
//        // 等于基础类型
//        if (BasicTypeInfo.isBasicTypeInfo(schemaTypeInfo)) {
//            return schemaTypeInfo.toFlinkColumnString(name);
//        }
//        if (schemaTypeInfo instanceof RecordTypeInfo) {
//            String start = String.format("`%s` ROW <", name);
//            List<String> schemaLines = new ArrayList<>();
//            ((RecordTypeInfo) schemaTypeInfo).getFields().forEach((key, value) -> {
//                schemaLines.add(convert(key, value));
//            });
//            String end = ">";
//            return String.format("%s\n%s\n%s",
//                    start,
//                    schemaLines.stream()
//                            .collect(Collectors.joining(",\n")),
//                    end);
//        }
//        if (schemaTypeInfo instanceof MapTypeInfo) {
//            return convert(name, ((MapTypeInfo) schemaTypeInfo).getValue());
//        }
//        if (schemaTypeInfo instanceof ArrayTypeInfo) {
//            return convert(name, ((ArrayTypeInfo) schemaTypeInfo).getElementType());
//        }
//        return "";
//    }
}
