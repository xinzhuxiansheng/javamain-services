package com.javamain.schema;

import com.javamain.schema.typeinfo.SchemaTypeInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SchemaField {
    private String name;
    private SchemaTypeInfo schemaTypeInfo;
    private List<SchemaField> subFields = new ArrayList<>();

    public void addSubField(SchemaField field) {
        subFields.add(field);
    }
}
