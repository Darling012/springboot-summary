package com.learn.mvc.excel.easyexcel.common;

import java.util.List;

@FunctionalInterface
public interface ImportBatchSave<T> {
    void accept(List<T> successList, List<ImportErrVo> failList);
}
