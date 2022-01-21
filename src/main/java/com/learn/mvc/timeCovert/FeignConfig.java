package com.learn.mvc.timeCovert;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.spring.SpringContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public TimeFeignService timeFeignService() {
        return Feign.builder()
                    .encoder(new GsonEncoder()) // 设置编码器
                    .decoder(new GsonDecoder()) // 设置解码器
                    .contract(new SpringContract()) // 设置使用 SpringContract 契约
                    .target(TimeFeignService.class, "localhost:8091/time"); // 目标地址
    }
}
