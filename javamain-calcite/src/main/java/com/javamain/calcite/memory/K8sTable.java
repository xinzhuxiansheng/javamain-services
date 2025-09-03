package com.javamain.calcite.memory;

import org.apache.calcite.DataContext;
import org.apache.calcite.linq4j.AbstractEnumerable;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeName;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class K8sTable extends AbstractTable implements ScannableTable {

  private PodInfoList podInfoList;

  public K8sTable(PodInfoList podInfoList) {
    this.podInfoList = podInfoList;
  }

  @Override
  public Enumerable<Object[]> scan(DataContext root) {
    return new AbstractEnumerable<Object[]>() {

      @Override
      public Enumerator<Object[]> enumerator() {
        // 获取 PodInfo 迭代器
        Iterator<PodInfo> iterator = podInfoList.getIterator();

        return new Enumerator<Object[]>() {
          private Object[] current;

          @Override
          public Object[] current() {
            return current;
          }

          @Override
          public boolean moveNext() {
            // 检查是否还有下一个元素
            if (iterator.hasNext()) {
              PodInfo podInfo = iterator.next();

              // 处理 podInfo 为 null 的情况
              if (podInfo != null) {
                current = new Object[]{
                        podInfo.getNamespace() != null ? podInfo.getNamespace() : "",
                        podInfo.getPodName() != null ? podInfo.getPodName() : "",
                        podInfo.getReady() != null ? podInfo.getReady() : "",
                        podInfo.getStatus() != null ? podInfo.getStatus() : "",
                        podInfo.getRestartCount(), // int 类型不会有 null 问题
                        podInfo.getAge() != null ? podInfo.getAge() : "",
                        podInfo.getPodIP() != null ? podInfo.getPodIP() : "",
                        podInfo.getNode() != null ? podInfo.getNode() : ""
                };
              } else {
                // podInfo 为 null 时返回默认值
                current = new Object[]{"", "", "", "", 0, "", "", ""};
              }
              return true;
            }
            current = null;
            return false;
          }

          @Override
          public void reset() {

          }

          @Override
          public void close() {

          }
        };
      }

      @NotNull
      @Override
      public Iterator<Object[]> iterator() {
        Iterator<PodInfo> iterator = podInfoList.getIterator();
        return new Iterator<Object[]>() {
          @Override
          public boolean hasNext() {
            return iterator.hasNext();
          }

          // 迭代返回每行数据
          @Override
          public Object[] next() {
            PodInfo next = iterator.next();
            return new Object[]{
                    next.getNamespace(),
                    next.getPodName(),
                    next.getReady(),
                    next.getStatus(),
                    next.getRestartCount(),
                    next.getAge(),
                    next.getPodIP(),
                    next.getNode()};
          }
        };
      }
    };
  }

  @Override
  public RelDataType getRowType(RelDataTypeFactory typeFactory) {
    return typeFactory.builder()
            .add("namespace", typeFactory.createSqlType(SqlTypeName.VARCHAR))
            .add("podName", typeFactory.createSqlType(SqlTypeName.VARCHAR))
            .add("ready", typeFactory.createSqlType(SqlTypeName.VARCHAR))
            .add("status", typeFactory.createSqlType(SqlTypeName.VARCHAR))
            .add("restartCount", typeFactory.createSqlType(SqlTypeName.INTEGER))
            .add("age", typeFactory.createSqlType(SqlTypeName.VARCHAR))
            .add("podIP", typeFactory.createSqlType(SqlTypeName.VARCHAR))
            .add("node", typeFactory.createSqlType(SqlTypeName.VARCHAR))
            .build();
  }
}