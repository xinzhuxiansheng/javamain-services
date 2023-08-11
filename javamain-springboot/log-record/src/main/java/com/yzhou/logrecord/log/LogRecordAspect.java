package com.yzhou.logrecord.log;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class LogRecordAspect {

    /**
     * 切入点
     */
    @Pointcut("execution(public * com.yzhou.logrecord.controller.*.*(..))")
    public void LogRecordAspect() {
    }

    /**
     * 在方法前执行
     *
     * @param joinPoint
     */
    @Before("LogRecordAspect()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("doBefore");
    }

    /**
     * 在方法后执行
     *
     * @param joinPoint
     */
    @After("LogRecordAspect()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("doAfter");
    }

    /**
     * 在方法执行后返回一个结果后执行
     *
     * @param joinPoint
     */
    @AfterReturning("LogRecordAspect()")
    public void doAfterReturning(JoinPoint joinPoint) {

        System.out.println("doAfterReturning");
    }

    /**
     * 在方法执行过程中抛出异常的时候执行
     *
     * @param joinPoint
     */
    @AfterThrowing("LogRecordAspect()")
    public void doAfterThrowing(JoinPoint joinPoint) {
        System.out.println("doAfterThrowing");
    }

    /**
     * 环绕通知，在执行前后都可以使用，这个方法参数必须为ProceedingJoinPoint，proceed()方法就是被切面的方法，
     *
     * @Before、@After、@AfterReturning和@AfterThrowing四个方法可以使用JoinPoint，JoinPoint包含类名、被切面的方法名、参数等信
     * @param joinPoint
     * @return
     * @throws Throwable
     */
//    @Around("LogRecordAspect()")
//    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("doAround");
//        return joinPoint.proceed();
//    }
    @Autowired
    private ApplicationContext applicationContext;

    @Around("@annotation(logRecordAnnotation)")
    public Object logRecord(ProceedingJoinPoint joinPoint, LogRecordAnnotation logRecordAnnotation) throws Throwable {
        LogRecordAnnotation[] annotations;

        // 注解解析：若解析失败直接不执行日志切面逻辑
        try {
            Method method = getMethod(joinPoint);
            annotations = method.getAnnotationsByType(LogRecordAnnotation.class);
        } catch (Throwable throwable) {
            return joinPoint.proceed();
        }


        // 获取方法参数
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        // 创建评估上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(applicationContext));
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }

        // 解析注解中的SpEL表达式
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(logRecordAnnotation.success());
        String result = expression.getValue(context, String.class);

        // 你可以使用result了，这里就是user.name的值
        System.out.println("Result: " + result);

        // 继续执行原方法
        return joinPoint.proceed();
    }

    private Method getMethod(JoinPoint joinPoint) {
        Method method = null;
        try {
            Signature signature = joinPoint.getSignature();
            MethodSignature ms = (MethodSignature) signature;
            Object target = joinPoint.getTarget();
            method = target.getClass().getMethod(ms.getName(), ms.getParameterTypes());
        } catch (NoSuchMethodException e) {
            log.error("OperationLogAspect getMethod error", e);
        }
        return method;
    }

}
