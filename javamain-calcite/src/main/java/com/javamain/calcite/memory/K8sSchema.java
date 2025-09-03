package com.javamain.calcite.memory;

import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;

import java.util.HashMap;
import java.util.Map;

public class K8sSchema extends AbstractSchema {
  Map<String, Table> tableMap = new HashMap<>();

  public void addTable(String name, Table table) {
    tableMap.put(name, table);
  }

  public K8sSchema() {
  }

  @Override
  protected Map<String, Table> getTableMap() {
    return tableMap;
  }
}

