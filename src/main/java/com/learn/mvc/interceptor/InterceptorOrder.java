package com.learn.mvc.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基于Java的反射机制，属于面向切面编程（AOP）的一种运用
 */
@Slf4j
// @Component
public class InterceptorOrder implements HandlerInterceptor {
    /**
     * applyPreHandle这个方法执行，就是执行的拦截器的preHandler方法
     * 但这个过程中，controller方法没有从request中获取请求参数，组装方法参数；--而是在ha.handle这个方法的时候，才会组装参数。虽然没法得到方法的参数，但是可以获得IOC的bean哦。-- 从request中读取
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("2、执行-----InterceptorOrder----preHandle-----");
        // String encryptData = new BufferedReader(new InputStreamReader(request.getInputStream()))
        //         .lines().collect(Collectors.joining(System.lineSeparator()));
        // log.info(encryptData);
        // RequestWrapper requestWrapper = new RequestWrapper(request);
        // String body = requestWrapper.getBody();
        // log.info(body);
        // throw new RuntimeException("拦截器抛出异常");
        return true;
    }


    /**
     * 当controller内部有异常，postHandler方法是不会执行的
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("17、执行-----InterceptorOrder----postHandle-----");
    }


    /**
     * 不管controller内部是否有异常，都会执行此方法；此方法还会有个Exception ex这个参数；如果有异常，ex会有异常值；没有异常 此值为null。注意点如果controller内部有异常，但异常被@ControllerAdvice 异常统一捕获的话，ex也会为null
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info("18、执行-----InterceptorOrder----afterCompletion-----");
    }

}
