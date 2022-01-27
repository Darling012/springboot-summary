package com.learn.mvc.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AOP操作可以对操作进行横向的拦截,最大的优势在于他可以获取执行方法的参数,对方法进行统一的处理。常见使用日志,事务,请求参数安全验证等
 */
@Aspect
// @Component
@Slf4j
public class AspectOrder {

    @Pointcut("execution(public * com.learn.mvc.controller.StudentController.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        log.info("执行-----AspectOrder----doBefore-----");
    }

    @After("log()")
    public void doAfter() {
        log.info("执行-----AspectOrder----After-----");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();//获取request
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();//获取response
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        log.info("执行-----AspectOrder----AfterReturning-----");
    }
}
