package com.javamain.schema.column;

import com.javamain.schema.typeinfo.SchemaTypeInfo;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Data
public class ConfluentAvroColumn02 extends Column{
    private SchemaTypeInfo schemaTypeInfo;
    private List<ConfluentAvroColumn02> subFields;

    public ConfluentAvroColumn02(){
        subFields = new ArrayList<>();
    }

    public void addSubField(ConfluentAvroColumn02 field) {
        subFields.add(field);
    }
}
