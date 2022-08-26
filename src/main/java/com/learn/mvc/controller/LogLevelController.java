package com.learn.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springboot-summary
 * @version:
 * @description:
 * @author: ling
 * @create: 2022-08-26 15:37
 **/
@RestController
@RequestMapping("api/v1/log")
@Slf4j
public class LogLevelController {
    @GetMapping
    public void test(){
        log.trace("我是trace级别日志");
        log.debug("我是debug级别日志");
        log.info("我是info级别日志");
        log.warn("我是warn级别日志");
        log.error("我是error级别日志");
    }
}
