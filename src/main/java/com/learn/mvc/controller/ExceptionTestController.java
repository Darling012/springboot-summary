package com.learn.mvc.controller;

import com.learn.mvc.annotation.RestUrl;
import com.learn.mvc.exception.EntityCrudException;
import com.learn.mvc.exception.enums.GlobalExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-01-20 15:15
 **/
@RestUrl("api/v1/exception")
@Slf4j
public class ExceptionTestController {

    @GetMapping("controller")
    public void testControllerException(){
        log.info("controller抛出了一个异常：[{}],全局处理器可以捕获","EntityCrudException");
        throw new EntityCrudException(GlobalExceptionCode.ADD_ENTITY_ERROR);
    }
    @GetMapping("interceptor")
    public void testInterceptorException(){
        log.info("interceptor#preHandle抛出了一个异常被兜底异常处理器捕获");
        // GlobalExceptionAdvice 和 GlobalResponseAdvice 都会走到
        log.info("interceptor#postHandle有问题");
        log.info("interceptor#afterCompletion抛出了一个异常被兜底异常处理器捕获");
    }
    @GetMapping("filter")
    public void testFilterException(){
        log.info("filter抛出了一个异常没有被兜底异常处理器捕获");
    }



}
