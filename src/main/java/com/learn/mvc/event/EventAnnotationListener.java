package com.learn.mvc.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-01-26 13:38
 **/
@Component
@Slf4j
public class EventAnnotationListener {
    @Async("asyncServiceExecutor")
    @EventListener
    public void onApplicationEvent(EventDemo event) {

        log.info("receiver:[{}],线程为:[{}]", event.getMessage(), Thread.currentThread()
                                                                     .getName());
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        log.info("requestAttributes-------{}", requestAttributes.getAttribute("hello", 0));
    }
}
