package com.javamain.schema.column;

import com.javamain.schema.typeinfo.SchemaTypeInfo;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class ConfluentAvroColumn extends Column{
    private SchemaTypeInfo schemaTypeInfo;
}
