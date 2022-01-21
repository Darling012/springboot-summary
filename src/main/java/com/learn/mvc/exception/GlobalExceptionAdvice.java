package com.learn.mvc.exception;

import com.learn.mvc.body.pojo.RespResult;
import com.learn.mvc.exception.enums.GlobalExceptionCode;
import com.learn.mvc.exception.enums.HttpStateCode;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

/**
 * @program: demo
 * @description: 全局异常处理
 * @author: ling
 * @create: 2020-03-10 21:36
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 请求参数绑定到对象上校验不通过会抛出 BindException
     * requestParams
     *
     * @param e 普通参数校验校验不通过
     * @return ResponseResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public RespResult<Void> bindExceptionHandler(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        log.error("参数绑定异常[{}]:[{}]", fieldError.getField(), fieldError.getDefaultMessage());
        log.error(ExceptionUtil.getMessage(e));
        return RespResult.fail(HttpStateCode.PARAM_BIND_ERROR.getCode(), "请求参数 " + fieldError.getDefaultMessage() + "不能为空");
    }

    /**
     * 对象参数接收请求体校验不通过会抛出 MethodArgumentNotValidException
     * RequestBody
     *
     * @param e 参数验证异常
     * @return ResponseInfo
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespResult<Void> parameterExceptionHandler(MethodArgumentNotValidException e) {
        log.error("请求体校验不通过：{}", e.getMessage());
        log.error(ExceptionUtil.getMessage(e));
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return RespResult.fail(HttpStateCode.PARAM_VALID_ERROR.getCode(), fieldError.getDefaultMessage());
            }
        }
        return RespResult.fail(HttpStateCode.PARAM_VALID_ERROR.getCode(), e.getMessage());
    }

    /**
     * @param e 参数验证异常
     * @return ResponseInfo
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public RespResult<Void> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("Method Argument Type Mismatch", e);
        return RespResult.fail(HttpStateCode.PARAM_TYPE_ERROR.getCode(), e.getMessage());
    }


    /**
     * 普通参数校验校验不通过会抛出 ConstraintViolationException
     * requestParam
     *
     * @param e 普通参数校验校验不通过
     * @return ResponseResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public RespResult<Void> constraintViolationExceptionHandler(ConstraintViolationException e) {
        log.error("Constraint Violation", e);
        return RespResult.fail(HttpStateCode.PARAM_VALID_ERROR.getCode(), e.getMessage());
    }

    /**
     * 忽略参数异常处理器
     * 缺少RequestParam
     *
     * @param e 忽略参数异常
     * @return ResponseResult
     * @RequestParam 可设置required为false关闭参数是否必须
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RespResult<Void> parameterMissingExceptionHandler(MissingServletRequestParameterException e) {
        log.error("缺少请求参数：{}", e.getParameterName());
        log.error(ExceptionUtil.getMessage(e));
        return RespResult.fail(HttpStateCode.PARAM_MISS.getCode(), "请求参数 " + e.getParameterName() + " 不能为空");
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public RespResult<Void> noHandlerFoundException(NoHandlerFoundException e) {
        log.error("404 Not Found", e);
        return RespResult.fail(HttpStateCode.NOT_FOUND.getCode(), "请求参数 " + e.getMessage() + " 不能为空");
    }

    /**
     * 缺少请求体异常处理器
     * requestBody 为空
     *
     * @param e 缺少请求体异常
     * @return ResponseResult
     * @RequestBody 可设置required为false关闭参数是否必须
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RespResult<Void> parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
        log.error("Message Not Readable", e);
        return RespResult.fail(HttpStateCode.MSG_NOT_READABLE.getCode(), "参数体不能为空");
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RespResult<Void> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("Request Method Not Supported", e);
        return RespResult.fail(HttpStateCode.METHOD_NOT_SUPPORTED.getCode(), "请求方式错误");
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public RespResult<Void> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("Media Type Not Supported", e);
        return RespResult.fail(HttpStateCode.MEDIA_TYPE_NOT_SUPPORTED.getCode(), "参数体不能为空");
    }

    /**
     * 必填参数缺失 ServletRequestBindingException
     *
     * @param e 普通参数校验校验不通过
     * @return ResponseResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServletRequestBindingException.class)
    public RespResult<Void> servletRequestBindingExceptionHandler(ServletRequestBindingException e) {
        log.error("必填参数缺失", e);
        return RespResult.fail(HttpStateCode.PARAM_MISS.getCode(), "请求参数 " + e.getMessage() + " 不能为空");
    }

    /**
     * 自定义参数错误异常处理器
     *
     * @param e 自定义参数
     * @return ResponseInfo
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ServiceException.class})
    public RespResult<Void> paramExceptionHandler(ServiceException e) {
        log.error("业务异常{}", e.getMessage());
        log.error(ExceptionUtil.getMessage(e));
        return RespResult.fail(e.getGlobalStateCode().getCode(), e.getMessage());
    }

     /**
     * 增删改查错误异常处理器
     *
     * @param e 自定义参数
     * @return ResponseInfo
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({EntityCrudException.class})
    public RespResult<Void> entityNotFoundException(EntityCrudException e) {
        log.error("业务异常{}", e.getMessage());
        log.error(ExceptionUtil.getMessage(e));
        return RespResult.fail(e.getGlobalStateCode());
    }

    /**
     * 统一处理未知异常
     * 如果 @ExceptionHandler 注解中未声明要处理的异常类型，则默认为参数列表中的异常类型
     */
    @ExceptionHandler
    public RespResult<Void> handleUnknownException(Exception e) {
        // 未知异常
        log.error("捕获到未经处理的未知异常, {}", e.getMessage());
        log.error("", e);
        return RespResult.fail(GlobalExceptionCode.ERROR);
    }

}
