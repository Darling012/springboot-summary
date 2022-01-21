package com.learn.mvc.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

// 事件发布
@RestController
@RequestMapping("event")
@Slf4j
public class EventDemoPublish {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @GetMapping("{msg}")
    public void publish(@PathVariable  String msg) {
        EventDemo demo = new EventDemo(this, msg);
        applicationEventPublisher.publishEvent(demo);
        log.info("controller线程为{}",Thread.currentThread().getName());
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        requestAttributes.setAttribute("hello", "word",0);
        log.info("requestAttributes{}",requestAttributes.getAttribute("hello", 0));
    }
}
