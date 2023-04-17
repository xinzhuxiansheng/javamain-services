package com.javamain.mybatis.web;

import com.google.gson.Gson;
import com.javamain.mybatis.common.util.MybatisUtils;
import com.javamain.mybatis.dao.UserMapper;
import com.javamain.mybatis.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserServlet extends HttpServlet {
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        User user = gson.fromJson(reader, User.class);

        // Process the user data (e.g., save it to a database or perform some calculations)
        user.setAge(user.getAge() + 1);

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        gson.toJson(user, resp.getWriter());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = null;

        SqlSessionFactory sqlSessionFactory = MybatisUtils.sqlSessionFactory;
        // 2. 从 SqlSessionFactory 中获取 SqlSession
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // 3. 获取 Mapper 接口的实例
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            // 4. 调用 Mapper 接口的方法执行 SQL 操作
            int userId = Integer.parseInt(req.getParameter("userId"));
            user = userMapper.getUserById(userId);

            // 5. 处理查询结果
            if (user != null) {
                System.out.println("User ID: " + user.getId());
                System.out.println("User Name: " + user.getName());
                System.out.println("User Age: " + user.getAge());
            } else {
                System.out.println("User not found.");
            }
        }

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        gson.toJson(user, resp.getWriter());
    }
}