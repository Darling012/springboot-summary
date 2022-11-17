package com.learn.mvc.controller.pojo;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class UserInfo {
    private Integer id;
    @NotBlank
    private String name;
    private String sex;
    private Integer age;
}
