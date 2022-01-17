package com.learn.mvc.exception;


import com.learn.mvc.exception.enums.GlobalStateCode;

/**
 * @program: luban
 * @description: 基础异常类
 * @author: ling
 * @create: 2020-04-26 22:20
 **/
public class BaseException extends RuntimeException{
    private static final long serialVersionUID = -8090380168632585815L;

    private GlobalStateCode globalStateCode;

    public BaseException(GlobalStateCode globalStateCode) {
        this.globalStateCode = globalStateCode;
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * for better performance
     *
     * @return Throwable
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }

    public GlobalStateCode getGlobalStateCode() {
        return globalStateCode;
    }
}
