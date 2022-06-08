package com.learn.mvc.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-06-08 11:39
 **/
@Configuration
@Slf4j
public class MyBeanAnnotation {
    @Bean(initMethod = "init",destroyMethod = "")
    public BeanAnnotation beanAnnotation(){
        return new BeanAnnotation();
    }

    class BeanAnnotation{
        private  void init(){
            log.info("init----系统启动成功后执行,{}", "@Bean(initMethod = “xxx”, destroyMethod = “xxx”)");
        }
    }
}
