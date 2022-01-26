package com.learn.mvc.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 配置个spring管理的线程池
 * ThreadPoolTaskExecutor spring提供
 */
@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfig {
    @Value("${async.executor.thread.core_pool_size:6}")
    private int corePoolSize;
    @Value("${async.executor.thread.max_pool_size:6}")
    private int maxPoolSize;
    @Value("${async.executor.thread.queue_capacity:9999}")
    private int queueCapacity;
    @Value("${async.executor.thread.name.prefix:darling-async-}")
    private String namePrefix;

    @Bean(name = "asyncServiceExecutor")
    public ThreadPoolTaskExecutor asyncServiceExecutor() {
        log.info("start asyncServiceExecutor");
        // ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        executor.setTaskDecorator(new ContextCopyingDecorator());
        //配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //配置队列大小
        executor.setQueueCapacity(queueCapacity);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(namePrefix);
        // 用了ThreadFactory 线程名前缀就失效了
        executor.setThreadFactory(r -> {
            Thread thread = new Thread(r);
            thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler("自定义线程池异常捕获器"));
            return thread;
        });
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}
