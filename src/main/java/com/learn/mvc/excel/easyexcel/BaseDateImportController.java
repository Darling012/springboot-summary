package com.learn.mvc.excel.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.learn.mvc.excel.easyexcel.common.ImportListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: cntytz
 * @version:
 * @description: 基础数据导入
 * @author: ling
 * @create: 2020-09-14 17:42
 **/
@Controller
@RequestMapping("excel")
public class BaseDateImportController {
    @Autowired
    private BaseDataImportService importService;

    @PostMapping("upload")
    public void upload(MultipartFile file, HttpServletResponse response) throws IOException {
        EasyExcel.read(file.getInputStream(), BaseImportEntity.class,
                       new BaseDataImportListener(importService, response)).sheet().doRead();
    }

    @PostMapping("uploads")
    public void uploads(MultipartFile file, HttpServletResponse response) throws IOException {
        EasyExcel.read(file.getInputStream(), BaseImportEntitys.class,
                       new ImportListener(importService::saveImportDatas,response)
                      ).sheet().doRead();
    }


}
