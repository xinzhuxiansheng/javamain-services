package com.yzhou.demo.controller;

import com.yzhou.demo.common.util.Log2KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzhou
 * @date 2022/7/29
 */
@RestController
@RequestMapping("/test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "test01", method = RequestMethod.GET)
    @ResponseBody
    public void test01() {
        logger.info("11111");
        Log2KafkaProducer.send("2222");
    }
}
