package com.learn.mvc.body.pojo;

import lombok.Data;

import java.util.List;

/**
 * @program: yunti
 * @description: 分页返回体
 * @author: ling
 * @create: 2020-04-11 18:17
 **/
@Data
public class PageResult<T> {
    /**
     * 当前页记录数
     */
    private Integer recordCount;
    /**
     * 总记录数
     */
    private Integer totalCount;
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页条数
     */
    private Integer pageSize;
    /**
     * 总页数
     */
    private Integer totalPage;

    private List<T> list;
}
