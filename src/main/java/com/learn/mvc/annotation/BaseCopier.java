package com.learn.mvc.annotation;


/**
 * @program: luban
 * @description: 字段一一对应
 * @author: ling
 * @create: 2020-04-14 22:25
 **/

public interface BaseCopier<T, V> {
    /**
     * 实体转VO
     *
     * @param t VO
     * @return 实体
     */
    V entityToVo(T t);

    /**
     * VO 转实体
     *
     * @param v VO
     * @return 实体
     */
    T voToEntity(V v);

}
