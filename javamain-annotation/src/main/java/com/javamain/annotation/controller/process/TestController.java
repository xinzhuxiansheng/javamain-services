package com.javamain.annotation.controller.process;


import com.javamain.annotation.common.enums.RenderType;
import com.javamain.annotation.common.enums.RequestMethod;
import com.javamain.annotation.controller.annotation.Controller;
import com.javamain.annotation.controller.annotation.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Controller(path = "/test")
public class TestController {
    private  static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Mapping(path = "/getselectdata", requestMethod = RequestMethod.GET, renderType = RenderType.JSON)
    public Map<String,Object> getSelectData(){

        return null;
    }
}
