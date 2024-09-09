package com.javamain.schema.typeinfo;

import lombok.Data;


@Data
public class MapTypeInfo extends SchemaTypeInfo {
    private String typeName = "map";
    private SchemaTypeInfo value;

    public MapTypeInfo(SchemaTypeInfo value) {
        this.value = value;
    }

    public String toFlinkColumnString(String name) {
//        return String.format("`%s` MAP<STRING,%s> COMMENT ''", name,"STRING");
//        String start = String.format("`%s` MAP < STRING, ", name);
//        String fieldsStr = SQLConvertUtils.convertaaaa("", value);
//        String end = " >";
//        return String.format("%s\n%s\n%s", start, fieldsStr, end);
        return null;
    }
}
