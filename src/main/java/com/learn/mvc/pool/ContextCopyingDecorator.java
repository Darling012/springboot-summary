package com.learn.mvc.pool;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class ContextCopyingDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        RequestAttributes context = RequestContextHolder.currentRequestAttributes();
        String ctxLogId =  MDC.get("ctxLogId");
        return () -> {
            try {
                RequestContextHolder.setRequestAttributes(context);
                MDC.put("ctxLogId", ctxLogId);
                runnable.run();
            } finally {
                // 线程结束，清空这些信息，否则可能造成内存泄漏
                RequestContextHolder.resetRequestAttributes();
                MDC.clear();
            }
        };
    }
}
