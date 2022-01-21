package com.learn.mvc.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author Darling
 */ /*
 * 定义一个事件
 */
public class EventDemo extends ApplicationEvent {
    private String message;
    public EventDemo(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
