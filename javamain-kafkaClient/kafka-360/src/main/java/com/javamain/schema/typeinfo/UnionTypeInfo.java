package com.javamain.schema.typeinfo;

import lombok.Data;

// 没有用
@Data
public class UnionTypeInfo extends SchemaTypeInfo {
    private String typeName = "union";
    private SchemaTypeInfo value;

    public UnionTypeInfo(SchemaTypeInfo value){
        this.value = value;
    }

    @Override
    public String toFlinkColumnString(String name) {
        return null;
    }
}
