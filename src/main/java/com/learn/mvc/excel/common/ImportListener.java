package com.learn.mvc.excel.common;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.learn.mvc.excel.easyexcel.BaseErrorVo;
import com.learn.mvc.excel.easyexcel.BaseImportEntity;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-06-08 17:45
 **/
@Slf4j
public class ImportListener<T extends ImportErrVo> extends AnalysisEventListener<T> {

    private final ImportBatchSave<T> batchConsumer;
    private final HttpServletResponse response;

    public ImportListener(ImportBatchSave<T> batchConsumer, HttpServletResponse response) {
        this.batchConsumer = batchConsumer;
        this.response = response;
    }

    /**
     * 每隔500条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 500;
    // 成功结果集
    List<T> successList = new ArrayList<>();
    // 失败结果集
    List<ImportErrVo> failList = new ArrayList<>();
    // 验证器对象
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        Set<ConstraintViolation<T>> errorSet = validator.validate(data);
        if (errorSet.isEmpty()) {
            successList.add(data);
        } else {
            errorSet.forEach(e -> {
                log.error(e.getMessageTemplate());
            });
            ImportErrVo errorVo = new ImportErrVo();
            BeanUtils.copyProperties(data, errorVo);
            errorVo.setErrorReason(
                    errorSet.stream().map(ConstraintViolation::getMessageTemplate).reduce(";", String::concat));
            failList.add(errorVo);
        }
        if (successList.size() >= BATCH_COUNT) {
            saveData(successList, failList);
            successList.clear();
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData(successList, failList);
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData(List<T> successList, List<ImportErrVo> failList) {
        log.info("{}条数据，开始存储数据库！", successList.size());
        batchConsumer.accept(response, successList, failList);
        log.info("存储数据库成功！");
    }
}
