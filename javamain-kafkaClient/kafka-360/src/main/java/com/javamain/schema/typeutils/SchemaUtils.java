package com.javamain.schema.typeutils;

import com.javamain.schema.column.ConfluentAvroColumn02;
import com.javamain.schema.convert.KafkaTypeConvert;
import com.javamain.schema.typeinfo.*;
import com.javamain.schema.typeinfo.base.*;
import org.apache.avro.Schema;

import java.util.List;

public class SchemaUtils {
    private static KafkaTypeConvert kafkaTypeConvert = new KafkaTypeConvert();

    public static ConfluentAvroColumn02 convertToSchemaField(Schema.Field avroField) {
        return convertToSchemaField(avroField.name(), avroField.schema());
    }

    public static ConfluentAvroColumn02 convertToSchemaField(String name, Schema schema) {
        ConfluentAvroColumn02 column = new ConfluentAvroColumn02();
        column.setName(name);
        column.setType(kafkaTypeConvert.convert(schema));
        switch (schema.getType()) {
            case RECORD:
                column.setSchemaTypeInfo(new RecordTypeInfo());
                for (Schema.Field field : schema.getFields()) {
                    column.addSubField(convertToSchemaField(field.name(), field.schema()));
                }
                break;
            case ENUM:
                column.setSchemaTypeInfo(new EnumTypeInfo());
                break;
            case ARRAY:
                column.setSchemaTypeInfo(new ArrayTypeInfo(convertToTypeInfo(schema.getElementType())));
                column.addSubField(convertToSchemaField(
                        isAvroComplexType(schema.getElementType()) ? schema.getElementType().getName() : "", schema.getElementType()));
                break;
            case MAP:
                column.setSchemaTypeInfo(new MapTypeInfo(convertToTypeInfo(schema.getValueType())));
                column.addSubField(convertToSchemaField(
                        isAvroComplexType(schema.getValueType()) ? schema.getValueType().getName() : "", schema.getValueType()));
                break;
            case UNION:
                Schema actualSchema = null;
                // 针对3个字段 则直接默认返回 String
                if (schema.getTypes().size() > 2) {
                    column.setSchemaTypeInfo(new StringTypeInfo());
                    break;
                }
                if (schema.getTypes().size() == 2
                        && schema.getTypes().get(0).getType() == Schema.Type.NULL) {
                    actualSchema = schema.getTypes().get(1);
                } else if (schema.getTypes().size() == 2
                        && schema.getTypes().get(1).getType() == Schema.Type.NULL) {
                    actualSchema = schema.getTypes().get(0);
                } else if (schema.getTypes().size() == 1) {
                    actualSchema = schema.getTypes().get(0);
                }
                column.setSchemaTypeInfo(new UnionTypeInfo(convertToTypeInfo(actualSchema)));
                column.addSubField(convertToSchemaField(
                        isAvroComplexType(actualSchema) ? actualSchema.getName() : "", actualSchema));
                break;
            case FIXED:
                column.setSchemaTypeInfo(Types.Fixed(schema.getLogicalType()));
                break;
            case STRING:
                column.setSchemaTypeInfo(new StringTypeInfo());
                break;
            case BYTES:
                column.setSchemaTypeInfo(Types.Bytes(schema.getLogicalType()));
                break;
            case INT:
                column.setSchemaTypeInfo(new IntTypeInfo(schema.getLogicalType()));
                break;
            case LONG:
                column.setSchemaTypeInfo(Types.Long(schema.getLogicalType()));
                break;
            case FLOAT:
                column.setSchemaTypeInfo(new FloatTypeInfo());
                break;
            case DOUBLE:
                column.setSchemaTypeInfo(new DoubleTypeInfo());
                break;
            case BOOLEAN:
                column.setSchemaTypeInfo(new BooleanTypeInfo());
                break;
            case NULL:
                column.setSchemaTypeInfo(new VoidTypeInfo());
                break;
        }
        return column;
        //throw new IllegalArgumentException("Unsupported Avro type '" + schema.getType() + "'.");
    }

    private static SchemaTypeInfo convertToTypeInfo(Schema schema) {
        switch (schema.getType()) {
            case RECORD:
                final List<Schema.Field> fields = schema.getFields();

                final SchemaTypeInfo[] types = new SchemaTypeInfo[fields.size()];
                final String[] names = new String[fields.size()];
                for (int i = 0; i < fields.size(); i++) {
                    final Schema.Field field = fields.get(i);
                    types[i] = convertToTypeInfo(field.schema());
                    names[i] = field.name();
                }
                return Types.Record(names, types);
            case ENUM:
                return Types.Enum();
            case ARRAY:
                return Types.OBJECT_ARRAY(convertToTypeInfo(schema.getElementType()));
            case MAP:
                return Types.Map(convertToTypeInfo(schema.getValueType()));
            case UNION:
                Schema actualSchema = null;
                // 针对3个字段 则直接默认返回 String
                if (schema.getTypes().size() > 2) {
                    return Types.String();
                }
                if (schema.getTypes().size() == 2
                        && schema.getTypes().get(0).getType() == Schema.Type.NULL) {
                    actualSchema = schema.getTypes().get(1);
                } else if (schema.getTypes().size() == 2
                        && schema.getTypes().get(1).getType() == Schema.Type.NULL) {
                    actualSchema = schema.getTypes().get(0);
                } else if (schema.getTypes().size() == 1) {
                    actualSchema = schema.getTypes().get(0);
                }
                return convertToTypeInfo(actualSchema);
            case FIXED:
                return Types.Fixed(schema.getLogicalType());
            case STRING:
                return Types.String();
            case BYTES:
                return Types.Bytes(schema.getLogicalType());
            case INT:
                return Types.Int(schema.getLogicalType());
            case LONG:
                return Types.Long(schema.getLogicalType());
            case FLOAT:
                return Types.Float();
            case DOUBLE:
                return Types.Double();
            case BOOLEAN:
                return Types.Boolean();
            case NULL:
                return Types.Void();
        }
        throw new IllegalArgumentException("Unsupported Avro type '" + schema.getType() + "'.");
    }

    public static boolean isAvroComplexType(Schema schema) {
        if (schema.getType().equals(Schema.Type.RECORD)
                || schema.getType().equals(Schema.Type.ARRAY)) {
            return true;
        }
        return false;
    }
}
