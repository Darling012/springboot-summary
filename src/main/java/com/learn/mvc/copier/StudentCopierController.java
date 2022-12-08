package com.learn.mvc.copier;

import com.learn.mvc.annotation.RestUrl;
import com.learn.mvc.copier.StudentCopier;
import com.learn.mvc.controller.pojo.StudentInfo;
import com.learn.mvc.domain.entities.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;

@RestUrl("api/v1/student/copier")
@RequiredArgsConstructor
@Slf4j
public class StudentCopierController {

    private final StudentCopier studentCopier;
    private final ConversionService mvcConversionService;

    private Student student;
    @PostConstruct
    void init(){
        student = new Student();
        student.setId(1L);
        student.setName("张三");
        student.setAge(12);

    }

    @GetMapping("1")
    public StudentInfo test1() {
        return studentCopier.entityToVo(student);
    }


    @GetMapping("2")
    public StudentInfo test2() {
        return mvcConversionService.convert(student,StudentInfo.class);
    }

    @GetMapping("3")
    public Student test3() {
        StudentInfo info = new StudentInfo();
        info.setName("zhangsan");
        info.setAge(1);
        return mvcConversionService.convert(info,Student.class);
    }
}
