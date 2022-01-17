package com.learn.mvc.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: luban
 * @description: 自定义全局错误码
 * @author: ling
 * @create: 2020-04-26 21:52
 **/
@AllArgsConstructor
@Getter
public enum GlobalExceptionCode implements GlobalStateCode {
    /**
     *
     */
    ADD_ENTITY_ERROR("1", "新增实体数据异常"),
    /**
     *
     */
    QUERY_ENTITY_ERROR("2", "查询实体数据异常"),
    /**
     *
     */
    UPDATE_ENTITY_ERROR("3", "更新实体数据异常"),

    /**
     *
     */
    DELETE_ENTITY_ERROR("4", "删除实体数据异常"),
    /**
     *
     */
    ERROR("-1", "未知错误");

    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.message;
    }
}
