package com.learn.mvc.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 实现InitializingBean接口重写其afterPropertiesSet方法，实现DisposableBean接口重写destroy方法
 */
@Component
@Slf4j
public class MyInitializingBean implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
         log.info("init----系统启动成功后执行,{}","InitializingBean");
    }
}
