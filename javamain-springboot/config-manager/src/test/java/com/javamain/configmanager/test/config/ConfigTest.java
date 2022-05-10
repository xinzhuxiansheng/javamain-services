package com.javamain.configmanager.test.config;

import com.javamain.configmanager.SpringbootApplication;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yzhou
 * @date 2022/5/5
 */
@SpringBootTest(classes = {SpringbootApplication.class})
public class ConfigTest {
    private static final Logger logger = LoggerFactory.getLogger(ConfigTest.class);

    @Autowired
    private FlinkJobConfig jobConfig;
    @Autowired
    private MysqlConfig mysqlConfig;
    @Autowired
    private AutoMysqlConfig autoMysqlConfig;

    @Test
    public void testValueAnnotations() {
        //logger.info("job name: {}",jobConfig.name);
        //logger.info("mysql url: {}", mysqlConfig.url);
        logger.info("mysql password: {}", mysqlConfig.getPassword());
    }

    @Test
    public void testConfigurationProperties() {
        logger.info("mysqlConfigAuto password: {}", autoMysqlConfig.password);
    }

}
