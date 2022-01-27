package com.learn.mvc.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

/**
 * 过滤器依赖于servlet容器。在实现上，基于函数回调，它可以对几乎所有请求进行过滤，一个过滤器实例只能在容器初始化时调用一次。
 * 使用过滤器的目的是用来做一些过滤操作，获取我们想要获取的数据，比如：在过滤器中修改字符编码；在过滤器中修改HttpServletRequest的一些参数，包括：过滤低俗文字、危险字符等
 */
// @Component
@Slf4j
public class FilterOrder implements Filter {

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("执行-----FilterOrder---------");

    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("1、执行-----FilterOrder----start-----");
        // 单体要放到最前边一个Servlet filter里  微服务就放到webFlux filter里
        MDC.put("ctxLogId", UUID.randomUUID().toString());
        chain.doFilter(request, response);
        // ServletRequest requestWrapper = null;
        // if (request instanceof HttpServletRequest) {
        //     requestWrapper = new RequestWrapper((HttpServletRequest) request);
        // }
        // if (requestWrapper == null) {
        //     chain.doFilter(request, response);
        // } else {
        //     chain.doFilter(requestWrapper, response);
        // }
        // throw new RuntimeException("过滤器抛出异常");
        log.info("19、执行-----FilterOrder----end-----");
         MDC.clear();
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        log.info("执行-----FilterOrder----destroy-----");
    }

}
