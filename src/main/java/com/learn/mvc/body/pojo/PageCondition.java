package com.learn.mvc.body.pojo;

import lombok.Data;

/**
 * @program: yunti
 * @description: 分页查询参数
 * @author: ling
 * @create: 2020-04-12 12:08
 **/
@Data
public class PageCondition<P> {
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 查询条件
     */
    private P condition;
}
