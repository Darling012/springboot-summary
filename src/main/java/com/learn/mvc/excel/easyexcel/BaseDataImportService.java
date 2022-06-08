package com.learn.mvc.excel.easyexcel;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface BaseDataImportService {

    void saveImportData(HttpServletResponse response, List<BaseImportEntity> successList, List<BaseErrorVo> failList);
}
