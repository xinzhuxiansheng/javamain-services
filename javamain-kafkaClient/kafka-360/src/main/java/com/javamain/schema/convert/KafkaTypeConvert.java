package com.javamain.schema.convert;

import com.javamain.schema.column.RowField;
import org.apache.avro.LogicalType;
import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;

import java.util.ArrayList;
import java.util.List;

public class KafkaTypeConvert implements ITypeConvert {
  @Override
  /* Avro* */
  public FlinkDataType convert(String dbType) {
    return null;
  }

  public FlinkDataType convert(Schema schema) {
    switch (schema.getType()) {
      case RECORD:
        return FlinkDataType.ROW;
      case ARRAY:
        return FlinkDataType.ARRAY;
      case MAP:
        return FlinkDataType.MAP;
      case FIXED:
        return FlinkDataType.DECIMAL;
      case ENUM:
      case STRING:
        return FlinkDataType.STRING;
      case BYTES:
        if (schema.getLogicalType() instanceof LogicalTypes.Decimal) {
          return FlinkDataType.DECIMAL;
        }
        return FlinkDataType.BYTES;
      case UNION:
        final Schema actualSchema;
        if (schema.getTypes().size() == 2
            && schema.getTypes().get(0).getType() == Schema.Type.NULL) {
          actualSchema = schema.getTypes().get(1);
        } else if (schema.getTypes().size() == 2
            && schema.getTypes().get(1).getType() == Schema.Type.NULL) {
          actualSchema = schema.getTypes().get(0);
        } else {
          actualSchema = schema.getTypes().get(0);
        }
        return convert(actualSchema);
      case INT:
        // logical date and time type
        final LogicalType logicalType = schema.getLogicalType();
        if (logicalType == LogicalTypes.date()) {
          return FlinkDataType.DATE;
        } else if (logicalType == LogicalTypes.timeMillis()) {
          return FlinkDataType.TIME;
        }
        return FlinkDataType.INT;
      case LONG:
        if (schema.getLogicalType() == LogicalTypes.timestampMillis()
            || schema.getLogicalType() == LogicalTypes.timestampMicros()) {
          return FlinkDataType.TIMESTAMP;
        } else if (schema.getLogicalType() == LogicalTypes.timeMicros()) {
          return FlinkDataType.TIME;
        }
        return FlinkDataType.BIGINT;
      case FLOAT:
        return FlinkDataType.FLOAT;
      case DOUBLE:
        return FlinkDataType.DOUBLE;
      case BOOLEAN:
        return FlinkDataType.BOOLEAN;
    }
    throw new IllegalArgumentException("Unsupported Avro type '" + schema.getType() + "'.");
  }

  @Override
  public String convertToDB(FlinkDataType columnType) {
    return null;
  }

  public List<RowField> convertToRowField(Schema.Field field) {
    if (field.schema().getType().equals(Schema.Type.UNION)) {
      Schema actualSchema = getChildSchemaByUnionType(field.schema());
      return parseMulFields(actualSchema.getFields());
    }

    List<Schema.Field> subFields = field.schema().getFields();
    return parseMulFields(subFields);
  }

  private List<RowField> parseMulFields(List<Schema.Field> fields) {
    List<RowField> out = new ArrayList<>();
    for (Schema.Field subField : fields) {
      out.add(
          RowField.builder()
              .name(subField.name())
              .type(convert(subField.schema()).toString())
              .build());
    }
    return out;
  }

  @Override
  public String convertToMapField(Schema.Field field) {
    if (field.schema().getType().equals(Schema.Type.UNION)) {
      Schema actualSchema = getChildSchemaByUnionType(field.schema());
      return convert(actualSchema.getValueType()).toString();
    }
    return convert(field.schema().getValueType()).toString();
  }

  @Override
  public Object convertToArrayField(Schema.Field field, FlinkDataType type) {
    if (field.schema().getType().equals(Schema.Type.UNION)) {
      Schema actualSchema = getChildSchemaByUnionType(field.schema());
      if (actualSchema.getElementType().getType().equals(Schema.Type.RECORD)) {
        return parseMulFields(actualSchema.getElementType().getFields());
      } else {
        return convert(actualSchema.getElementType());
      }
    }

    if (field.schema().getElementType().getType().equals(Schema.Type.RECORD)) {
      return parseMulFields(field.schema().getElementType().getFields());
    } else {
      return convert(field.schema().getElementType());
    }
  }

  private Schema getChildSchemaByUnionType(Schema schema) {
    Schema actualSchema;
    if (schema.getTypes().size() == 2 && schema.getTypes().get(0).getType() == Schema.Type.NULL) {
      actualSchema = schema.getTypes().get(1);
    } else if (schema.getTypes().size() == 2
        && schema.getTypes().get(1).getType() == Schema.Type.NULL) {
      actualSchema = schema.getTypes().get(0);
    } else {
      actualSchema = schema.getTypes().get(0);
    }
    return actualSchema;
  }
}
