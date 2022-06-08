package com.learn.mvc.excel.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @program: cntytz
 * @version:
 * @description: 基础信息导入失败
 * @author: ling
 * @create: 2020-09-16 15:29
 **/
@Data
public class BaseErrorVo extends BaseImportEntity {
    /**
     * 失败原因
     **/
    @ExcelProperty("失败原因")
    // @ExcelProperty(index = 19,value = "失败原因")
    private String errorReason;
}
