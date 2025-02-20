package com.javamain.schema.typeinfo;

import lombok.Data;

@Data
public class ArrayTypeInfo extends SchemaTypeInfo {
    private String typeName = "array";
    private SchemaTypeInfo elementType;

    public ArrayTypeInfo(SchemaTypeInfo elementType) {
        this.elementType = elementType;
    }

    public String toFlinkColumnString(String name) {
        if(elementType instanceof RecordTypeInfo){

        }else if(elementType instanceof MapTypeInfo){
            return ((MapTypeInfo) elementType).toFlinkColumnString(name);
        }
        return String.format("`%s` ARRAY<STRING> COMMENT ''", name);
    }
}
