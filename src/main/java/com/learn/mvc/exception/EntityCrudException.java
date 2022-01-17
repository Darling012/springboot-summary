package com.learn.mvc.exception;


import com.learn.mvc.exception.enums.GlobalStateCode;

/**
 * @program: luban
 * @description: 实体未查询到
 * @author: ling
 * @create: 2020-04-14 22:19
 **/
public class EntityCrudException extends BaseException {
    private static final long serialVersionUID = -2257293122034202488L;

    public EntityCrudException(GlobalStateCode globalStateCode, String msg) {
        super(msg);
    }

    public EntityCrudException(GlobalStateCode globalStateCode) {
        super(globalStateCode);
    }


    public EntityCrudException(String message) {
        super(message);
    }

    public EntityCrudException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityCrudException(Throwable cause) {
        super(cause);
    }

    public EntityCrudException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
