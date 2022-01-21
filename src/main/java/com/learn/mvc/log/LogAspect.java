package com.learn.mvc.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 操作日志记录处理
 *
 * @author ruoyi
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    // 配置织入点
    @Pointcut("@annotation(com.learn.mvc.annotation.Log)")
    public void logPointCut() {
    }

    @Around(value = "logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        log.info("9、执行-----LogAspect----doAround-----");
        return pjp.proceed();
    }

    @Before(value = "logPointCut()")
    public void doBefore() {
        log.info("10、----LogAspect----doBefore-----------------记录日志");
    }

    @After(value = "logPointCut()")
    public void doAfter() {
        log.info("11、----LogAspect----doAfter-----------------记录日志");
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        log.info("12、----LogAspect----doAfterReturning-----------------记录日志");
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        log.info("----LogAspect----doAfterThrowing-----------------记录日志");
    }


}
