package com.javamain.schema.convert;

import com.javamain.schema.column.RowField;
import com.javamain.schema.util.Asserts;
import org.apache.avro.Schema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ITypeConvert {

//  default String convertToDB(Column column) {
//    return convertToDB(column.getType());
//  }

  FlinkDataType convert(String dbType);

  FlinkDataType convert(Schema schema);

  List<RowField> convertToRowField(Schema.Field field);

  String convertToMapField(Schema.Field field);

  Object convertToArrayField(Schema.Field field,FlinkDataType type);

  String convertToDB(FlinkDataType columnType);

  default Object convertValue(ResultSet results, String columnName, String javaType)
      throws SQLException {
    if (Asserts.isNull(javaType)) {
      return results.getString(columnName);
    }
    switch (javaType.toLowerCase()) {
      case "string":
        return results.getString(columnName);
      case "double":
        return results.getDouble(columnName);
      case "int":
        return results.getInt(columnName);
      case "float":
        return results.getFloat(columnName);
      case "bigint":
        return results.getLong(columnName);
      case "decimal":
        return results.getBigDecimal(columnName);
      case "date":
      case "localdate":
        return results.getDate(columnName);
      case "time":
      case "localtime":
        return results.getTime(columnName);
      case "timestamp":
        return results.getTimestamp(columnName);
      case "blob":
        return results.getBlob(columnName);
      case "boolean":
        return results.getBoolean(columnName);
      case "byte":
        return results.getByte(columnName);
      case "bytes":
        return results.getBytes(columnName);
      default:
        return results.getString(columnName);
    }
  }
}
