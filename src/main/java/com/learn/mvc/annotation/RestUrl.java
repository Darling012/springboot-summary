package com.learn.mvc.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-01-18 14:42
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping
public @interface RestUrl {
    @AliasFor("path")
    String[] value() default {};

    @AliasFor("value")
    String[] path() default {};
}
