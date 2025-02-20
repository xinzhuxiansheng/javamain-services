package com.javamain.schema.typeinfo;

import lombok.Data;

@Data
public class RecordTypeInfo extends SchemaTypeInfo {

    /**
     * 递归
     */
    public String toFlinkColumnString(String name) {
        return String.format("`%s` STRING COMMENT ''", name);
    }
}
