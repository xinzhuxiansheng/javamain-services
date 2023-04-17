package com.javamain.mybatis.common.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MybatisUtils {

    public static SqlSessionFactory sqlSessionFactory;

    public static void initSqlSession() throws IOException {
        // 1. 从 XML 配置文件中构建 SqlSessionFactory
        String resource = "mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }
}
