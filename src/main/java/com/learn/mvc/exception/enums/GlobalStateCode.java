package com.learn.mvc.exception.enums;

import java.io.Serializable;

/**
 * @author Darling
 */
public interface GlobalStateCode extends Serializable {
    /**
     * 全局状态码
     *
     * @return 全局状态码
     */
    String getCode();

    /**
     * 全局提示信息
     *
     * @return 全局提示信息
     */
    String getMsg();
}
