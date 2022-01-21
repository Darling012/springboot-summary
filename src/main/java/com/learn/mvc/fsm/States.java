package com.learn.mvc.fsm;

/**
 * 状态枚举
 **/
public enum States {
    /**
     * 草稿
     */
    DRAFT,
    /**
     * 待发布
     */
    PUBLISH_TODO,
    /**
     * 发布完成
     */
    PUBLISH_DONE,
}
