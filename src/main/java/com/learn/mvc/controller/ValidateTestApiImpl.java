package com.learn.mvc.controller;

import com.learn.mvc.controller.copier.StudentCopier;
import com.learn.mvc.controller.pojo.StudentInfo;
import com.learn.mvc.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Component
@RequiredArgsConstructor
@Validated
public class ValidateTestApiImpl implements ValidateTestApi{
     private final StudentService studentService;
    private final StudentCopier studentCopier;

    @Override
    public StudentInfo createStudent(@Validated StudentInfo studentInfo) {
        studentInfo.setName(null);
        return studentInfo;
    }

    @Override
    // 不能加在这校验
    public Integer detail(@Min(6) Integer age) {
        return 6;
    }

    @Override
    public Integer getByAccount(Integer age) {
       return 11;
    }
}
