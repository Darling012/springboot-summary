package com.learn.mvc.controller.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserInfo {
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
}
