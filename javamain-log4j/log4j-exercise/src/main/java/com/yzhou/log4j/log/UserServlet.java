package com.yzhou.log4j.log;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserServlet extends HttpServlet {
     private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);
    private Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.debug("doGet debug log");
        logger.info("doGet info log");

        Map<String,Object> result = new HashMap<>();
        result.put("code",0);

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        gson.toJson(result, resp.getWriter());
    }
}