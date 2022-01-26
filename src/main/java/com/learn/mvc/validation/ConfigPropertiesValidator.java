package com.learn.mvc.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <see>https://mp.weixin.qq.com/s?__biz=MzAwMTk4NjM1MA==&mid=2247502146&idx=1&sn=4aa2cdcfe5895aa3431c9921249b966a&chksm=9ad3d303ada45a15ac2790490a29975b438a57e8652ae60e69ba0c4f8a2b70af7f67f9e7147c&scene=178&cur_album_id=1517924978380308484#rd</see>
 * 注意  为spring 提供
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
