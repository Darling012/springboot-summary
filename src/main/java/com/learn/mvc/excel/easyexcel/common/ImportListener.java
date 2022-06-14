package com.learn.mvc.excel.easyexcel.common;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.learn.mvc.excel.easyexcel.BaseErrorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private static final int BATCH_COUNT = 7;
    // 成功结果集
    List<T> successList = new ArrayList<>();
    // 失败结果集
    List<ImportErrVo> failList = new ArrayList<>();
    // 验证器对象
    private final Validator validator = Validation.buildDefaultValidatorFactory()
                                                  .getValidator();


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
                    errorSet.stream()
                            .map(ConstraintViolation::getMessageTemplate)
                            .reduce(";", String::concat));
            failList.add(errorVo);
        }
        if (successList.size() >= BATCH_COUNT) {
            log.info("已成功解析{}条数据准备存贮", successList.size());
            saveData(successList, failList);
            successList.clear();
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成最后剩{}条数据准备存贮", successList.size());
        saveData(successList, failList);
        log.info("所有数据解析完成！有" + failList.size() + "条不合法数据");
        if (!failList.isEmpty()) {
            try {
                response.setContentType("application/vnd.ms-excel");
                response.setCharacterEncoding("utf-8");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时mm分");
                String fileName = URLEncoder.encode(LocalDateTime.now()
                                                                 .format(formatter) + "不合法数据", "UTF-8")
                                            .replaceAll("\\+", "%20");
                response.setHeader("Content-disposition", "attachment;filename*=''" + fileName + ".xls");
                EasyExcel.write(response.getOutputStream(), BaseErrorVo.class)
                         .sheet("不合法数据")
                         .doWrite(failList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加上存储数据库
     */
    private void saveData(List<T> successList, List<ImportErrVo> failList) {
        batchConsumer.accept(successList, failList);
    }
}
