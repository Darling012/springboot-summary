package com.learn.mvc.controller;

import com.learn.mvc.exception.ExceptionUtil;
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
    public void test() {
        try {
            LogLevelController l = new LogLevelController();
            l.printA();

        } catch (Exception e) {
            log.trace("我是trace级别日志", e);
            log.debug("我是debug级别日志", e);
            log.info("我是info级别日志", e);
            log.warn("我是warn级别日志", e);
            log.error("我是error级别日志", e);
            System.out.println("-------");
            e.printStackTrace();
        }


    }

    @GetMapping("test2")
    public void test2() {
        try {
            System.out.println(1/0);
        } catch (Exception e) {
            //
            log.error("正常输出异常栈", e);
            //拼接java.lang.ArithmeticException: / by zero
            log.error("拼接" + e);
            //打印getMessage：/ by zero
            log.error("打印getMessage：{}", e.getMessage());
            e.printStackTrace();
            System.out.println(ExceptionUtil.getMessage(e));

        }

    }

    public void printA() {
        LogLevelController l = new LogLevelController();
        l.printB();

    }

    public void printB() {

        System.out.println(1/0);
    }
}
