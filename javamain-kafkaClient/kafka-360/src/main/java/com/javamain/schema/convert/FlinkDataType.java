package com.javamain.schema.convert;


import com.javamain.schema.column.ArrayColumn;
import com.javamain.schema.column.Column;
import com.javamain.schema.column.MapColumn;
import com.javamain.schema.column.RowColumn;

public enum FlinkDataType {
  TINYINT("TINYINT"),
  SMALLINT("SMALLINT"),
  INT("INT"),
  INTEGER("INT"), // 为了做类型转换
  BIGINT("BIGINT"),
  DECIMAL("DECIMAL"),
  FLOAT("FLOAT"),
  DOUBLE("DOUBLE"),
  BOOLEAN("BOOLEAN"),
  DATE("DATE"),
  TIME("TIME"),
  TIMESTAMP("TIMESTAMP"),
  TIMESTAMP_LTZ("TIMESTAMP_LTZ"),
  STRING("STRING"),
  VARCHAR("STRING"), // 为了做类型转换
  BYTES("BYTES"),
  BINARY("BINARY"),
  VARBINARY("VARBINARY"),
  MAP("MAP"),
  ROW("ROW"),
  RAW("RAW"),
  MULTISET("MULTISET"),
  ARRAY("ARRAY");

  private final String alias;

  FlinkDataType(String alias) {
    this.alias = alias;
  }

  public String getAlias() {
    return alias;
  }

  public static String getDisplayName(Column column) {
    switch (column.getType()) {
      case TIME:
        return column.getPrecision() != null
            ? String.format("TIME (%s)", column.getPrecision())
            : "TIME";
      case TIMESTAMP:
        return column.getPrecision() != null
            ? String.format("TIMESTAMP (%s)", column.getPrecision())
            : "TIMESTAMP";
      case DECIMAL:
        {
          if (column.getPrecision() == null && column.getScale() == null) {
            return "DECIMAL";
          } else if (column.getPrecision() != null && column.getScale() != null) {
            return String.format("DECIMAL(%s, %s)", column.getPrecision(), column.getScale());
          } else {
            return String.format("DECIMAL(%s)", column.getPrecision());
          }
        }
      case ROW:
        {
          if (column instanceof RowColumn) {
            RowColumn rowColumn = (RowColumn) column;
            return rowColumn.convertFieldSql();
          }
        }
      case MAP:
        {
          if (column instanceof MapColumn) {
            MapColumn mapColumn = (MapColumn) column;
            return mapColumn.convertFieldSql();
          }
        }
      case ARRAY:
        {
          if (column instanceof ArrayColumn) {
            ArrayColumn mapColumn = (ArrayColumn) column;
            return mapColumn.convertFieldSql();
          }
        }

      default:
        return column.getType().getAlias();
    }
  }

  public static boolean hasPrecision(FlinkDataType dataType) {
    return dataType == TIMESTAMP || dataType == DECIMAL || dataType == TIME;
  }

  public static boolean hasScale(FlinkDataType dataType) {
    return dataType == DECIMAL;
  }
}
