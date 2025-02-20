package com.javamain.schema.column;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RowField {
  private String name;
  private String type;
  private String comment;

  @Override
  public String toString() {
    return String.format("%s %s %s", name, type, comment == null ? "''" : comment);
  }
}
