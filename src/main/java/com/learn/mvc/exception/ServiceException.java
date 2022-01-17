package com.learn.mvc.exception;

import com.learn.mvc.exception.enums.GlobalStateCode;
import com.learn.mvc.exception.enums.HttpStateCode;
import lombok.Getter;

/**
 * 业务基础异常
 *
 * @author Darling
 */
public class ServiceException extends BaseException {
    private static final long serialVersionUID = 2359767895161832954L;

    @Getter
    private final GlobalStateCode globalStateCode;

    public ServiceException(String message) {
        super(message);
        this.globalStateCode = HttpStateCode.FAILURE;
    }

    public ServiceException(HttpStateCode resultCode) {
        super(resultCode.getMsg());
        this.globalStateCode = resultCode;
    }

    public ServiceException(HttpStateCode resultCode, String msg) {
        super(msg);
        this.globalStateCode = resultCode;
    }

    public ServiceException(HttpStateCode resultCode, Throwable cause) {
        super(cause);
        this.globalStateCode = resultCode;
    }

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
        this.globalStateCode = HttpStateCode.FAILURE;
    }
}
