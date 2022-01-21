package com.learn.mvc.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class WhenStartupA implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("另一汇总方法");
    }
}
