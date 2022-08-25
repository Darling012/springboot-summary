package com.learn.mvc.pool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AsyncTask {
    @SneakyThrows
    @Async
    public void doTask1() {
        log.info("异步调用了doTask1");
         throw new RuntimeException("异步调用doTask1抛出异常");
    }

    @Async
    public void doTask2() {
        log.info("异步调用了doTask2");
        throw new RuntimeException("异步调用doTask2抛出异常");
    }
}
