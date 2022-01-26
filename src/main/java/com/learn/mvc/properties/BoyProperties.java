package com.learn.mvc.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * 在使用配置文件校验时，必须使用@configurationproperties注解，@value注解不支持该功能。
 * @Valid 不起作用  要看下源码
 */

@Validated
@Component
@ConfigurationProperties(prefix = "boy")
public class BoyProperties {
    @NotBlank(message = "name不能为空")
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
