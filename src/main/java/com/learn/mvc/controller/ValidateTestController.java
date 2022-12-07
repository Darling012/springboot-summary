package com.learn.mvc.controller;

import com.learn.mvc.copier.StudentCopier;
import com.learn.mvc.controller.pojo.StudentInfo;
import com.learn.mvc.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-01-27 10:44
 **/
@RestController
@RequestMapping("api/v1/validate")
@Slf4j
@RequiredArgsConstructor
@Validated
public class ValidateTestController {
    private final StudentService studentService;
    private final StudentCopier studentCopier;

    /**
     *  requestBody 测试
     * @param studentInfo
     * @return
     */
    @PostMapping
    public StudentInfo createStudent(@RequestBody  StudentInfo studentInfo) {

        // studentService.createStudent(studentCopier.voToEntity(studentInfo));
        studentInfo.setName(null);
        return studentInfo;
    }

    /**
     * 路径变量测试
     * @param age
     * @return
     */
    @GetMapping("{age}")
    public @Max(6) Integer detail(@PathVariable("age") @Min(6) Integer age) {
        return 6;
    }

    // 查询参数
    @GetMapping("age")
    public Integer getByAccount(@Min(6) Integer age) {
        return 1;
    }
}
