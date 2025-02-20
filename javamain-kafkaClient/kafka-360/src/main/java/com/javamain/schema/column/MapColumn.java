package com.javamain.schema.column;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class MapColumn extends Column {

  // Kafka Schema Map类型的 key 必须字符串，这是由Avro的规范所决定的。
  private String fieldKType;
  private String fieldVType;

  public MapColumn() {}

  public String convertFieldSql() {
    return getType().toString() + "< " + fieldKType + ", " + fieldVType + ">";
  }
}
