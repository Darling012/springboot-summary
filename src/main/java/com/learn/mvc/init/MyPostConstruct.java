package com.learn.mvc.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <see>https://blog.csdn.net/u011291072/article/details/81813662</see>
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-06-08 11:33
 **/
@Component
@Slf4j
public class MyPostConstruct {
    static {
        log.info("init----系统启动成功后执行,{}", "静态代码块");
    }

    public MyPostConstruct() {
        log.info("init----系统启动成功后执行,{}", "构造器");
    }

    @PostConstruct
    public void init() {
        log.info("init----系统启动成功后执行,{}", "@PostConstruct注解");

    }
}
