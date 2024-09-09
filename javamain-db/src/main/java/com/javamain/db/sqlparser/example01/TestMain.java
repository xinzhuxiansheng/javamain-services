package com.javamain.db.sqlparser.example01;

import java.nio.charset.StandardCharsets;

/**
 * @author yzhou
 * @date 2022/12/21
 */
public class TestMain {

    public static void main(String[] args) throws Exception {
        testCreate();
    }

    public static void testCreate() throws Exception {
        String sql = "create table student id int32, name string, uid int64, (index name id uid)";
        Parser.Parse(sql.getBytes(StandardCharsets.UTF_8));

    }
}
