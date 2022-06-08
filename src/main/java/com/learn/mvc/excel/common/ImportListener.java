package com.learn.mvc.excel.common;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

/**
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-06-08 17:45
 **/
@Slf4j
public class ImportListener extends AnalysisEventListener<T> {
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
