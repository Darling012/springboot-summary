package com.learn.mvc.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.Executor;

@RestController
@Slf4j
public class TestController {
    // 3. 线程池中复制上下文
    @Autowired
    private Executor asyncServiceExecutor;

    @GetMapping("/setSession")
    public void setSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("name", "张三");
    }

    @GetMapping("/getSession")
    public void getSession() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        System.out.println("requestAttributes" + requestAttributes);
        if (requestAttributes != null) {
            String name = (String) requestAttributes.getAttribute("name", RequestAttributes.SCOPE_SESSION);
            log.info("controller线程------从session中获取数据:{}", name);

        }
        // ThreadPoolExecutor asyncServiceExecutor = new ThreadPoolExecutor(2, 2, 100, TimeUnit.SECONDS,new LinkedBlockingDeque());
        //2. 将主线程上下文信息共享给子线程，这样只需要写一遍，该线程下的所有子线程都会共享上下文数据
        // RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        asyncServiceExecutor.execute(() -> {
            // 1. 第一种单独复制，这样每个都要写
            // RequestContextHolder.setRequestAttributes(requestAttributes);
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            String name = (String) attributes.getAttribute("name", RequestAttributes.SCOPE_SESSION);
            log.info("线程池中线程------从session中获取数据:{}", name);
            throw new RuntimeException("子线程抛出异常");
        });
        log.info("子线程抛出异常,父线程不受影响");
        // Future<Integer> task = asyncServiceExecutor.submit(() -> {
        //     System.out.println(Thread.currentThread()
        //                              .getName());
        //     // 1. 第一种单独复制，这样每个都要写
        //     // RequestContextHolder.setRequestAttributes(requestAttributes);
        //      RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        //    String name = (String) attributes.getAttribute("name", RequestAttributes.SCOPE_SESSION);
        //     log.info("线程池中线程------从session中获取数据:{}", name);
        //     return 1/0;
        // });
        // try {
        //     Integer number = task.get();
        // } catch (Exception e) {
        //   log.info("捕获到future.get异常{}",e.getMessage());
        // }
    }
}
