package com.learn.mvc.excel.common;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-06-08 17:40
 **/
@Data
public class ImportErrVo {
 /**
     * 失败原因
     **/
    @ExcelProperty("失败原因")
    // @ExcelProperty(index = 19,value = "失败原因")
    private String errorReason;
}
