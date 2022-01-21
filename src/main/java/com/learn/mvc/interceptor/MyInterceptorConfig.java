package com.learn.mvc.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 程序执行的顺序是先进过滤器，再进拦截器，最后进切面。注意：如果拦截器中preHandle方法返回的为false时，则无法进入切面
 * 当程序有异常抛出时，回先进入切面，然后在进入自定义的ControllerAdvice中
 * 请求进来 会按照 filter -> interceptor -> controllerAdvice -> aspect  -> controller的顺序调用
 * 当controller返回异常 也会按照controller -> aspect -> controllerAdvice -> interceptor -> filter来依次抛出
 */
@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private InterceptorOrder InterceptorOrder;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(InterceptorOrder).addPathPatterns("/**");
    }
}
