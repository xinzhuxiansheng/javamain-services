package com.javamain.mybatis;

import com.javamain.mybatis.common.util.MybatisUtils;
import com.javamain.mybatis.web.JettyServer;

public class mybatisMain {
    public static void main(String[] args) throws Exception {

        MybatisUtils.initSqlSession();

        JettyServer jettyServer = new JettyServer();
        jettyServer.start();
    }




}
