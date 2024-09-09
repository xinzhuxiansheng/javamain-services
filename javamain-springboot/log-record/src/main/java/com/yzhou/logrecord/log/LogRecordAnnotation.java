package com.yzhou.logrecord.log;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LogRecordAnnotation {
    String success() default "";

    String fail() default "";

    String operator() default "";

    String bizNo() default "";

    String category() default "";

    String detail() default "";

    String condition() default "";
}
