package com.learn.mvc.body;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @program: luban
 * @description:
 * @author: ling
 * @create: 2020-04-12 00:03
 **/
@Configuration
public class BodyConverterConfig {
    @Bean
    @Order(1)
    public StringToResultHttpMessageConverter myConverter() {
        return new StringToResultHttpMessageConverter();
    }
}
