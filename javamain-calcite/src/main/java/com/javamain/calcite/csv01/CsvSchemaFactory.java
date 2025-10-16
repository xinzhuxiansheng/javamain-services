package com.javamain.calcite.csv01;

import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaFactory;
import org.apache.calcite.schema.SchemaPlus;

import java.util.Map;

public class CsvSchemaFactory implements SchemaFactory {
    
    @Override
    public Schema create(SchemaPlus parentSchema, String name, Map<String, Object> operand) {
        String directory = (String) operand.get("directory");
        return new CsvSchema(directory);
    }
}