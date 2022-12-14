package com.learn.mvc.controller.pojo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-01-18 14:04
 **/
@Data
@Valid
public class StudentInfo {

    @NotBlank
    private String name;
    private Integer age;
    // @Valid
    // private UserInfo userInfo;
    // @Valid
    // @NotEmpty
    // private List<UserInfo> userInfos;
}
