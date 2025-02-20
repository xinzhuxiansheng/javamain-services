package com.javamain.schema.column;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@SuperBuilder
public class RowColumn extends Column {
  public List<RowField> internalFields;

  public RowColumn() {}

  public String convertFieldSql() {
    if (!internalFields.isEmpty()) {
      return getType().toString()
          + "< "
          + internalFields.stream().map(RowField::toString).collect(Collectors.joining(", "))
          + " >";
    }
    return "";
  }
}
