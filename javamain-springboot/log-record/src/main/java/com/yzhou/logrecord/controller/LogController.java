package com.yzhou.logrecord.controller;

import com.yzhou.logrecord.log03.LogEntry;
import com.yzhou.logrecord.log03.ModuleType;
import com.yzhou.logrecord.log03.namespace.NamespaceLogTemplate;
import com.yzhou.logrecord.modal.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    //@LogRecord(success = "1", type = "2", bizNo = "3")
    @PostMapping(value = "/login")
    public String login(@RequestBody User user) {
        logger.info("request login params {} , {}", user.getUserId(), user.getName());
        return "OK";
    }

    @PostMapping(value = "/hello")
    public String hello(@RequestBody User user) {
        logger.info("request hello params {} , {}", user.getUserId(), user.getName());

        LogEntry.build()
                .moduleType(ModuleType.NAMESPACE)
                .bizNo("1")
                .user(user)
                .createTime(System.currentTimeMillis())
                .summaryTemplate(NamespaceLogTemplate.CREATE)
                .param("username","yzhou")
                .param("id","1")
                .save();

        return "OK";
    }

}
