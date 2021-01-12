package com.javamain.annotation;

import com.javamain.annotation.common.enums.RequestMethod;
import com.javamain.annotation.controller.annotation.Controller;
import com.javamain.annotation.controller.annotation.Mapping;
import com.xinzhuxiansheng.core.lang.ClassScaner;
import com.xinzhuxiansheng.core.utils.CollUtil;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 程序入口
 */
public class ProcessMain {
    private static final Logger logger  = LoggerFactory.getLogger(ProcessMain.class);

    public static void main(String[] args) {



        //step1，获取特定注解的类
        Set<Class<?>> classSet = ClassScaner.scanPackageByAnnotation("com.javamain.annotation.controller.process", Controller.class);

        if(CollUtil.isNotEmpty(classSet)){
            for(Class<?> cls :classSet){
                Controller controller = cls.getAnnotation(Controller.class);

                //获取Controller中所有的方法
                Method[] methods = cls.getMethods();
                for(Method method:methods){
                    Mapping mapping = method.getAnnotation(Mapping.class);
                    if(null != mapping){
                        addRoute(controller,mapping);
                    }
                }

            }
        }



    }


    public static void addRoute(Controller controller,Mapping mapping){
        //Controller+Mapping 唯一确定一个控制器的方法
        String path = controller.path()+mapping.path();
        HttpMethod method = RequestMethod.getHttpMethod(mapping.requestMethod());
        logger.info("path: {}, methd: {}, renderType: {}",path,method,mapping.renderType());
    }
}
