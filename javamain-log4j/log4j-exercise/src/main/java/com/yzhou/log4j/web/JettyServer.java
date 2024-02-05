package com.yzhou.log4j.web;

import com.yzhou.log4j.log.UserServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyServer {

    public void start() throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new UserServlet()), "/user");
        context.addServlet(new ServletHolder(new LogServlet()), "/log");

        server.start();
        server.join();
    }
}
