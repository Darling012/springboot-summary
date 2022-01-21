package com.learn.mvc.pool;

import lombok.extern.slf4j.Slf4j;

/**
 * 描述：     自己的MyUncaughtExceptionHanlder
 */
@Slf4j
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private String name;

    public MyUncaughtExceptionHandler(String name) {
        this.name = name;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.warn("[{}]捕获到子线程异常，异常为[{}]",name,e.getMessage());
    }
}
