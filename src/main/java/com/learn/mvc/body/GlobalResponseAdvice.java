package com.learn.mvc.body;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.mvc.body.pojo.RespResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局返回值
 *
 * @author Darling
 */
@Slf4j
@ControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 需要忽略的地址
     */
    private static String[] ignores = new String[]{
            //过滤swagger相关的请求的接口，不然swagger会提示base-url被拦截
            "/swagger-resources",
            "/v2/api-docs"
    };

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {

        final String returnTypeName = methodParameter.getParameterType()
                                                     .getSimpleName();
         if (String.class.getSimpleName().equals(returnTypeName) || RespResult.class.getSimpleName().equals(returnTypeName)) {
            return Boolean.FALSE;
        }

        if (RespResult.class.getSimpleName()
                            .equals(returnTypeName)) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        //判断url是否需要拦截
        if (this.ignoring(serverHttpRequest.getURI()
                                           .toString())) {
            return o;
        }

        // 返回值类型 void
        final String returnTypeName = methodParameter.getParameterType()
                                                     .getSimpleName();
        if ("void".equals(returnTypeName)) {
            return RespResult.success(null);
        }
        // 这种方式 Content-Type不是json
        // if (o instanceof String) {
        //     return objectMapper.writeValueAsString(RespResult.success(o));
        // }
        // 如果不是返回json数据则不做包装
        if (!mediaType.includes(MediaType.APPLICATION_JSON)) {
            return o;
        }

        return RespResult.success(o);
    }

    /**
     * 判断url是否需要拦截
     *
     * @param uri
     * @return
     */
    private boolean ignoring(String uri) {
        for (String string : ignores) {
            if (uri.contains(string)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
