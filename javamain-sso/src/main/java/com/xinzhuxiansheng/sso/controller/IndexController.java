package com.xinzhuxiansheng.sso.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author Tim
 * 2024/10/9 3:20
 */
@Controller
@RequestMapping("/")
public class IndexController {
  @ResponseBody
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(Model model) {
    return "Hello";
  }

  @ResponseBody
  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public String user(Model model) {
    return "My name is Tim.";
  }

  @RequestMapping("/logout")
  public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // 获取会话
    HttpSession session = request.getSession(false);
    if (session != null) {
      // 销毁会话
      session.invalidate();
    }
    // 重定向到登出成功页面
    response.sendRedirect("http://localhost:8443/cas/logout?service=http%3A%2F%2Flocalhost%3A8090%2F");
//    response.sendRedirect("http://localhost:8443/cas/logout");
  }

}
