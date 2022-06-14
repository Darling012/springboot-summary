package com.learn.mvc.excel.easyexcel;

import com.learn.mvc.excel.easyexcel.common.ImportErrVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface BaseDataImportService {

    void saveImportData(HttpServletResponse response, List<BaseImportEntity> successList, List<BaseErrorVo> failList);

    void saveImportDatas(List<BaseImportEntitys> successList, List<ImportErrVo> importErrVos);
}
