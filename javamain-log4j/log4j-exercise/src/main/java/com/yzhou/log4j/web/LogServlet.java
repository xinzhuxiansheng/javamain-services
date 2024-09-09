package com.yzhou.log4j.web;

import com.google.gson.Gson;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LogServlet.class);
    private Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String logLevel = req.getParameter("logLevel");
        logger.info("修改 log 日志");
        Configurator.setLevel("com.yzhou.log4j.log.UserServlet", logLevel.equals("info") ? Level.INFO : Level.DEBUG);
        Configurator.setLevel("org.eclipse.jetty.servlet", logLevel.equals("info") ? Level.INFO : Level.DEBUG);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        gson.toJson(result, resp.getWriter());
    }
}