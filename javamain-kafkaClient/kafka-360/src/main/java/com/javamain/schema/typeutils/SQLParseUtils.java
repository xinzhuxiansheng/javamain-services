package com.javamain.schema.typeutils;

import com.javamain.schema.column.Column;
import com.javamain.schema.column.ConfluentAvroColumn02;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SQLParseUtils {

    public static String toCreateSQLString(List<Column> columns) {
        String start = "CREATE TABLE TABLE_NAME (";
        System.out.println(start);
        List<String> columnsLines = new ArrayList<>();
        columns.forEach(c -> {
            ConfluentAvroColumn02 column02 = (ConfluentAvroColumn02) c;
            columnsLines.add(SQLConvertUtils02.convertaaaa(column02,false) );
        });
        System.out.println(columnsLines.stream()
                .collect(Collectors.joining(",\n")));

        String end = ")";
        System.out.println(end);
        return "";
    }
}
