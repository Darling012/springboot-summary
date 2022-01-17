package com.learn.mvc.body.pojo;

import com.learn.mvc.exception.enums.AliCodeEnum;
import com.learn.mvc.exception.enums.GlobalStateCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: demo
 * @description:
 * @author: ling
 * @create: 2020-03-10 21:10
 **/
@AllArgsConstructor
@Data
public class RespResult<T> implements Serializable {
    private static final long serialVersionUID = -1607997078886917048L;

    /**
     * 错误码.
     */
    private String code;

    /**
     * 提示信息.
     */
    private String msg;

    /**
     * 具体的内容.
     */
    private T data;


    public static <T> RespResult<T> success(T data) {
        return new RespResult<>(AliCodeEnum.OK.getCode(), AliCodeEnum.OK.getMsg(), data);
    }

    public static <T> RespResult<T> fail(GlobalStateCode globalStateCode) {
        return new RespResult<>(globalStateCode.getCode(), globalStateCode.getMsg(), null);
    }
    public static <T> RespResult<T> fail(String code,String msg) {
        return new RespResult<>(code, msg, null);
    }
}
