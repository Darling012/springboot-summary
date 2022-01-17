package com.learn.mvc.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(poolTaskExecutor());
    }
    @Bean
    public ThreadPoolTaskScheduler poolTaskExecutor(){
        ThreadPoolTaskScheduler executor =  new ThreadPoolTaskScheduler();
       executor.setPoolSize(5);
        return executor;
    }
}
