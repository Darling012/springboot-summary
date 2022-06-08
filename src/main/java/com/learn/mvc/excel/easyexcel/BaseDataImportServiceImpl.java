package com.learn.mvc.excel.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.learn.mvc.excel.common.ImportErrVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

/**
 * @program: cntytz
 * @version:
 * @description: BaseDataImportService
 * @author: ling
 * @create: 2020-09-14 17:43
 **/
@Service
@Slf4j
public class BaseDataImportServiceImpl implements BaseDataImportService {
    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    TransactionDefinition transactionDefinition;


    @Override
    public void saveImportData(HttpServletResponse response,
                               List<BaseImportEntity> successList,
                               List<BaseErrorVo> failList) {
        // 1 拆分存贮数据
        for (BaseImportEntity baseEntity : successList) {
            TransactionStatus transactionStatus = dataSourceTransactionManager
                    .getTransaction(transactionDefinition);
            try {
                //省市区县
                @NotBlank(message = "所属区县不能为空") String court = baseEntity.getCourt();
                @NotBlank(message = "所属区县不能为空") String settlement = baseEntity.getSettlement();
                // 保存数据库
                dataSourceTransactionManager.commit(transactionStatus);
            } catch (Exception e) {
                log.error("插入数据出现错误:{}", e.getMessage(), e);
                BaseErrorVo errorVo = new BaseErrorVo();
                BeanUtils.copyProperties(baseEntity, errorVo);
                errorVo.setErrorReason("请检查此条数据");
                failList.add(errorVo);
                dataSourceTransactionManager.rollback(transactionStatus);
            }
        }
        //    2 将失败数据返回前端
        if (failList.isEmpty()) {
            return;
        }
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

    @Override
    public void saveImportDatas(HttpServletResponse httpServletResponse, Collection collection, List<ImportErrVo> importErrVos) {

    }
}
