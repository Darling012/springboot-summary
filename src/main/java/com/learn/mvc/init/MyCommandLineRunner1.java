package com.learn.mvc.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Spring Boot 项目在启动时会遍历所有的 CommandLineRunner 的实现类并调用其中的 run 方法。
 * 如果整个系统中有多个 CommandLineRunner 的实现类，那么可以使用 @Order 注解对这些实现类的调用顺序进行排序（数字越小越先执行）。
 * run 方法的参数是系统启动是传入的参数，即入口类中 main 方法的参数（在调用 SpringApplication.run 方法时被传入 Spring Boot 项目中）
 *
 * 原文出自：www.hangge.com  转载请保留原文链接：https://www.hangge.com/blog/cache/detail_2508.html
 */

@Component
@Order(1)
public class MyCommandLineRunner1 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Runner1>>>"+ Arrays.toString(args));
    }
}



