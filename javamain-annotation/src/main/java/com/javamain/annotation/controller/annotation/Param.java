package com.javamain.annotation.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Param {
	
	/**
	 * 将使用什么样的键值读取对象，
	 * 对于field，就是他名字
	 * 对于method的parameter，需要指明
	 * @return 参数的key
	 */
	String key() default "";
	
	/**
	 * 提供设置缺省值
	 * @return 提供设置缺省值
	 */
	String defaultValue() default "";
	
	/**
     * 是否校验参数为空
     * @return true：校验参数 false：不校验参数
     */
    boolean notNull() default false;

    /**
     * 是否校验参数为空
     * @return true：校验参数 false：不校验参数
     */
    boolean notBlank() default false;

}
