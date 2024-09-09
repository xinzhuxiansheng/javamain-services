package com.javamain.schema.column;

import com.javamain.schema.convert.FlinkDataType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.StringJoiner;

@Setter
@Getter
@SuperBuilder
public class ArrayColumn extends Column {
  private FlinkDataType nestedType;
  private Object internalFields;

  public ArrayColumn() {}

  public String convertFieldSql() {
    if (nestedType.equals(FlinkDataType.ROW)) {
      List<RowField> list = (List<RowField>) internalFields;
      StringJoiner joiner = new StringJoiner(", ");
      for (RowField rowField : list) {
        joiner.add(rowField.toString());
      }
      return getType().toString() + "<" + nestedType.toString() + "< " + joiner.toString() + " >>";
    } else {
      return getType().toString() + "< " + internalFields.toString() + " >";
    }
  }
}
