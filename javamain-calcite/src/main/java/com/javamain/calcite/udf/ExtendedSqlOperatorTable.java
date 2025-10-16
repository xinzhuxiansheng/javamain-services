package com.javamain.calcite.udf;

import org.apache.calcite.sql.SqlOperator;
import org.apache.calcite.sql.util.ReflectiveSqlOperatorTable;

import java.util.ArrayList;
import java.util.List;

public class ExtendedSqlOperatorTable extends ReflectiveSqlOperatorTable {
    private static ExtendedSqlOperatorTable instance;

    public static final SqlBase64DecodeFunction BASE64DECODE = new SqlBase64DecodeFunction();

    public static synchronized ExtendedSqlOperatorTable instance() {
        if (instance == null) {
            instance = new ExtendedSqlOperatorTable();
            instance.init();
        }

        return instance;
    }

    @Override
    public List<SqlOperator> getOperatorList() {
        List<SqlOperator> list = new ArrayList<>();
        list.add(BASE64DECODE);
        // TODO: 添加其他 UDF
        // list.add(OTHER);
        return list;
    }

    // TODO: Task 2 add lower() and upper() functions.
}
