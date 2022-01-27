package com.learn.mvc.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @program: girl
 * @description:
 * @author: ling
 * @create: 2020-03-05 21:55
 **/
@Slf4j
// @ControllerAdvice(basePackages = "")//此处设置需要当前Advice执行的域 , 省略默认全局生效
public class RequestBodyAdviceOrder implements RequestBodyAdvice {
    @Override
    /** 此处如果返回false , 则不执行当前Advice的业务 */
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     *  读取参数前执行在此做些编码 / 解密 / 封装参数为对象的操作
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return
     * @throws IOException
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        log.info("3、执行-----RequestBodyAdviceOrder-----beforeBodyRead----");
        return inputMessage;
    }

    // 读取参数后执行
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
         log.info("4、执行-----RequestBodyAdviceOrder-----afterBodyRead----");
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("执行-----RequestBodyAdviceOrder-----handleEmptyBody----");
        return body;
    }
}
