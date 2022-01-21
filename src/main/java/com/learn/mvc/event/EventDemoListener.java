package com.learn.mvc.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 如下方式会使@Async失效
 *
 * 异步方法使用static修饰
 * 异步类没有使用@Component注解（或其他注解）导致spring无法扫描到异步类
 * 异步方法不能与被调用的异步方法在同一个类中
 * 类中需要使用@Autowired或@Resource等注解自动注入，不能自己手动new对象
 * 如果使用SpringBoot框架必须在启动类中增加@EnableAsync注解
 */
// 定义一个事件监听者
@Component
@Slf4j
public class EventDemoListener implements ApplicationListener<EventDemo> {
    @Override
    @Async("asyncServiceExecutor")
    public void onApplicationEvent(EventDemo event) {

        log.info("receiver:[{}],线程为:[{}]",event.getMessage(),Thread.currentThread()
                                                                    .getName());
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        log.info("requestAttributes-------{}", requestAttributes.getAttribute("hello", 0));
    }
}
