package com.learn.mvc.excel.easyexcel;

import com.learn.mvc.excel.common.ImportErrVo;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

public interface BaseDataImportService {

    void saveImportData(HttpServletResponse response, List<BaseImportEntity> successList, List<BaseErrorVo> failList);

    void saveImportDatas(HttpServletResponse httpServletResponse, Collection collection, List<ImportErrVo> importErrVos);
}
