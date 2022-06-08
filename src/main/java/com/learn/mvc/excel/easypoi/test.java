package com.learn.mvc.excel.easypoi;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.handler.inter.IExcelDataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class test {
    @RequestMapping("export")
    public void export(HttpServletResponse response){

        //模拟从数据库获取需要导出的数据
        List<Person> personList = new ArrayList<>();
        Person person1 = new Person("路飞","1",new Date());
        Person person2 = new Person("娜美","2",new Date());
        Person person3 = new Person("索隆","1",new Date());
        Person person4 = new Person("小狸猫","1", new Date());
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);

        //导出操作
        FileUtil.exportExcel(personList,"花名册","草帽一伙",Person.class,"海贼王.xls",response);
    }

    @RequestMapping("importExcel")
    public void importExcel(){
        String filePath = "F:\\aa.xls";
        //解析excel，
        List<Person> personList = FileUtil.importExcel(filePath,1,1,Person.class);
        //也可以使用MultipartFile,使用 FileUtil.importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass)导入
        System.out.println("导入数据一共【"+personList.size()+"】行");

        //TODO 保存数据库
    }
    @PostMapping("excelImport")
    public void excelImport(@RequestParam("file") MultipartFile file) {
        final Logger log = LoggerFactory.getLogger(test.class);
        ImportParams importParams = new ImportParams();
        // 数据处理
        IExcelDataHandler<User> handler = new UserExcelHandler();
        handler.setNeedHandlerFields(new String[] { "姓名" });// 注意这里对应的是excel的列名。也就是对象上指定的列名。
        importParams.setDataHanlder(handler);

        // 需要验证
        importParams.setNeedVerfiy(true);

        try {
            ExcelImportResult<User> result = ExcelImportUtil.importExcelMore(file.getInputStream(), User.class,
                    importParams);

            List<User> successList = result.getList();
            List<User> failList = result.getFailList();

            log.info("是否存在验证未通过的数据:" + result.isVerfiyFail());
            log.info("验证通过的数量:" + successList.size());
            log.info("验证未通过的数量:" + failList.size());

            for (User user : successList) {
                log.info("成功列表信息:ID=" + user.getId() + user.getName() + "-"
                        + new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthday()));
            }
            for (User user : failList) {
                log.info("失败列表信息:" + user.getName());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
