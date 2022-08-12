package com.learn.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
// @EnableScheduling
// @EnableAsync

public class SpringbootSummaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSummaryApplication.class, args);
    }

    // @Bean
    // public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
    //     ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    //     //配置核心线程数
    //     executor.setCorePoolSize(5);
    //     return executor;
    // }
}
