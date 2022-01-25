package com.learn.mvc.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 利用Validator 校验properties 配置文件
 */
public class ConfigPropertiesValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
