package com.javamain.schema.typeinfo.base;

import com.javamain.schema.typeinfo.BasicTypeInfo;
import lombok.Data;

@Data
public class VoidTypeInfo extends BasicTypeInfo {
    private String typeName = "void";

    public String toFlinkColumnString(String name) {
        return String.format("`%s` VOID", name);
    }
}
