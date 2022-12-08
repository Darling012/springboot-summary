package com.learn.mvc.controller;

import com.learn.mvc.controller.pojo.StudentInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("api/v2/validate")
public interface ValidateTestApi {
    /**
     *  requestBody 测试
     * @param studentInfo
     * @return
     */
    @PostMapping
    public StudentInfo createStudent(@RequestBody  StudentInfo studentInfo);

    /**
     * 路径变量测试
     * @param age
     * @return
     */
    @GetMapping("{age}")
     @Max(6) Integer detail(@PathVariable("age")  Integer age);

    // 查询参数
    @GetMapping("age")
     Integer getByAccount(@Min(6) Integer age) ;
}
