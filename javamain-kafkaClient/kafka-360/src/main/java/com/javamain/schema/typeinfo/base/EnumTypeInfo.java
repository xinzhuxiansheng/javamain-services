package com.javamain.schema.typeinfo.base;

import com.javamain.schema.typeinfo.BasicTypeInfo;
import lombok.Data;

@Data
public class EnumTypeInfo extends BasicTypeInfo {
    private String typeName="enum";

    public String toFlinkColumnString(String name) {
        return String.format("`%s` STRING", name);
    }
}
