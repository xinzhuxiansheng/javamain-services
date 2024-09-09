package com.yzhou.java.configmanager.runner;

import com.yzhou.scala.configmanager.config.InternalConfigHolder;
import com.yzhou.scala.configmanager.config.InternalOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author yzhou
 * @date 2022/5/10
 */
@Component
public class EnvInitializer implements ApplicationRunner {

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initInternalConfig(context.getEnvironment());
    }

    private void initInternalConfig(Environment springEnv) {
        InternalConfigHolder
                .keys()
                .stream()
                .filter(springEnv::containsProperty)
                .forEach(key -> {
                    InternalOption config = InternalConfigHolder.getConfig(key);
                    assert config != null;
                    InternalConfigHolder.set(config, springEnv.getProperty(key, config.classType()));
                });

        InternalConfigHolder.log();
    }
}
