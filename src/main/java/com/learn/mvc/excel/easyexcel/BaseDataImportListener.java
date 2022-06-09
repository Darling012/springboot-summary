package com.learn.mvc.excel.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @program: easyexcel
 * @version:
 * @description:
 * @author: ling
 * @create: 2020-09-15 14:09
 **/
@Slf4j
public class BaseDataImportListener extends AnalysisEventListener<BaseImportEntity> {
    private BaseDataImportService importService;
    private HttpServletResponse response;

    public BaseDataImportListener(BaseDataImportService importService, HttpServletResponse response) {
        this.importService = importService;
        this.response = response;
    }

    /**
     * 每隔500条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 7;
    // 成功结果集
    List<BaseImportEntity> successList = new ArrayList<>();
    // 失败结果集
    List<BaseErrorVo> failList = new ArrayList<>();
    // 验证器对象
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public void invoke(BaseImportEntity data, AnalysisContext context) {

        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        Set<ConstraintViolation<BaseImportEntity>> errorSet = validator.validate(data);
        if (errorSet.isEmpty()) {
            successList.add(data);
        } else {
            errorSet.forEach(e -> {
                log.error(e.getMessageTemplate());
            });
            BaseErrorVo errorVo = new BaseErrorVo();
            BeanUtils.copyProperties(data, errorVo);
            errorVo.setErrorReason(errorSet.stream().map(ConstraintViolation::getMessageTemplate).reduce(";", String::concat));
            failList.add(errorVo);
        }
        if (successList.size() >= BATCH_COUNT) {
            System.out.println(BATCH_COUNT+"条开始存贮数据");
            saveData(successList, failList);
            successList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("所有数据解析完成最后剩"+successList.size()+"条数据存贮");
        saveData(successList, failList);
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData(List<BaseImportEntity> successList, List<BaseErrorVo> failList) {
        log.info("{}条数据，开始存储数据库！", successList.size());
        importService.saveImportData(response, successList, failList);
        log.info("存储数据库成功！");
    }
}
