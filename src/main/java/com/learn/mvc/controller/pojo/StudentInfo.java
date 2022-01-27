package com.learn.mvc.controller.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-01-18 14:04
 **/
@Data
public class StudentInfo {

    @NotBlank
    private String name;
    private Integer age;
}
