package com.javamain.calcite.background;

import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.adapter.jdbc.JdbcSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class HelloCalciteReadStaticData {
    public static void main(String[] args) throws Exception {

        Class.forName("org.apache.calcite.jdbc.Driver");
        Properties info = new Properties();
        info.setProperty("lex", "JAVA");
        Connection connection =
                DriverManager.getConnection("jdbc:calcite:", info);
        CalciteConnection calciteConnection =
                connection.unwrap(CalciteConnection.class);
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        Schema schema = new ReflectiveSchema(new HrSchema());
        // Schema schema = jdbcSchema(calciteConnection);  // 核心
        rootSchema.add("hr", schema);
        Statement statement = calciteConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select d.deptno, min(e.empid) as empid \n"
                        + "from hr.emps as e\n"
                        + "join hr.depts as d\n"
                        + "  on e.deptno = d.deptno\n"
                        + "group by d.deptno\n"
                        + "having count(*) > 1");

        while (resultSet.next()) {
            int deptno = resultSet.getInt("deptno");
            int minEmp = resultSet.getInt("empid");
            System.out.println(deptno + "->" + minEmp);
        }

        resultSet.close();
        statement.close();
        connection.close();
    }

    public static Schema jdbcSchema(CalciteConnection calciteConnection) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/hr");
        dataSource.setUsername("root");
        dataSource.setPassword("12345678");
        Schema schema = JdbcSchema.create(calciteConnection.getRootSchema(), "hr", dataSource,
                null, "hr");
        return schema;
    }


    public static class HrSchema {
        public final Employee[] emps = {
                new Employee(100, "Bill", 1),
                new Employee(200, "Eric", 1),
                new Employee(150, "Sebastian", 3),
        };

        public final Department[] depts = {
                new Department(1, "LeaderShip"),
                new Department(2, "TestGroup"),
                new Department(3, "Development")
        };
    }

    public static class Employee {
        public final int empid;
        public final String name;
        public final int deptno;

        public Employee(int empid, String name, int deptno) {
            this.empid = empid;
            this.name = name;
            this.deptno = deptno;
        }
    }

    public static class Department {
        public final int deptno;
        public final String name;

        public Department(int deptno, String name) {
            this.deptno = deptno;
            this.name = name;
        }
    }
}
