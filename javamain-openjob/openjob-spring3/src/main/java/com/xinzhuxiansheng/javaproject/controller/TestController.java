package com.xinzhuxiansheng.sso.controller;

import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @PostMapping(value = "/test01")
    public String login(@RequestBody JSONObject user) {
        logger.info("request login params {} , {}", user.getInteger("userId"), user.getString("name"));
        return "OK";
    }

    @PostMapping(value = "/hello")
    public String hello(@RequestBody JSONObject user) {
        logger.info("request hello params {} , {}", user.getInteger("userId"), user.getString("name"));
        return "OK";
    }

}
