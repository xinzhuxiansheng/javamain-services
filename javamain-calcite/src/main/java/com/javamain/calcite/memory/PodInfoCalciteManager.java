package com.javamain.calcite.memory;

import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PodInfoCalciteManager {
  private static final Logger logger = LoggerFactory.getLogger(PodInfoCalciteManager.class);

  private PodInfoList podInfoList;
  private K8sSchema k8sSchema;
  private CalciteConnection calciteConnection;
  private SchemaPlus rootSchema;

  public PodInfoCalciteManager() throws SQLException {

    this.podInfoList = PodInfoList.create();
    Table k8sTable = new K8sTable(this.podInfoList);
    this.k8sSchema = new K8sSchema();
    this.k8sSchema.addTable("podInfoTable", k8sTable);

    // Initialize Calcite connection
    Properties info = new Properties();
    info.setProperty("caseSensitive", "true");
    info.setProperty("unquotedCasing", "UNCHANGED");
//    info.setProperty("quoting", "BACK_TICK");
    Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
    this.calciteConnection = connection.unwrap(CalciteConnection.class);
    this.rootSchema = calciteConnection.getRootSchema();
    this.rootSchema.add("podInfoSchema", k8sSchema);
  }

  public void updatePodInfoList(List<PodInfo> newPodInfoList) {
    this.podInfoList.setPodInfoist(newPodInfoList);
    System.out.println("PodInfoList has been updated.");
  }

  public List<PodInfo> executeQuery(String sql) {
    List<PodInfo> podInfoList = new ArrayList<>();
    try {
      ResultSet resultSet = executeQuerySql(sql);
      while (resultSet.next()) {
        PodInfo podInfo = new PodInfo();
        podInfo.setNamespace(resultSet.getString("namespace"));
        podInfo.setPodName(resultSet.getString("podName"));
        podInfo.setReady(resultSet.getString("ready"));
        podInfo.setStatus(resultSet.getString("status"));
        podInfo.setRestartCount(resultSet.getInt("restartCount"));
        podInfo.setAge(resultSet.getString("age"));
        podInfo.setPodIP(resultSet.getString("podIP"));
        podInfo.setNode(resultSet.getString("node"));
        podInfoList.add(podInfo);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return podInfoList;
  }

  private ResultSet executeQuerySql(String sql) throws SQLException {
    Statement statement = calciteConnection.createStatement();
    return statement.executeQuery(sql);
  }
}