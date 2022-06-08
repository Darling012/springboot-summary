package com.learn.mvc.excel.common;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@FunctionalInterface
public interface ImportBatchSave<T> {
    void accept(HttpServletResponse response, List<T> successList, List<ImportErrVo> failList);
}
