package com.javamain.featuredemos.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class CustomAnnotationBeanPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 获取Bean的名字列表
        String[] beanNames = registry.getBeanDefinitionNames();

        // 从容器中移除这些Bean
        for (String beanName : beanNames) {
            if("checkPermissionAspect".equalsIgnoreCase(beanName)){
                registry.removeBeanDefinition(beanName);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // Do nothing
    }
}
