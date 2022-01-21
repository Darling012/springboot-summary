package com.learn.mvc.controller;

import com.learn.mvc.annotation.RestUrl;
import com.learn.mvc.body.pojo.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

/**
 *  返回结果示例
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-01-17 16:14
 **/
@RestUrl("api/v1/result")
@RequiredArgsConstructor
@Slf4j
public class ResultTestController {

    @GetMapping("void")
    public void testVoid(){
        log.info("执行了一个返回值为{}类型函数","void");
    }
    @GetMapping("str")
    public String testString(){
        log.info("执行了一个返回值为{}类型函数","String");
        return "String";
    }

    @GetMapping("list")
    public List<Integer> testList(){
        log.info("执行了一个返回值为{}类型函数","List");
         List<Integer> list = Arrays.asList(1,2,3);
        return list;
    }

     @GetMapping("page")
    public PageResult<Integer> testPage(){
        log.info("执行了一个返回值为{}类型函数","page");
        PageResult<Integer> result = new PageResult<>();
        result.setRecordCount(10);
        result.setTotalCount(20);
        result.setPageNo(1);
        result.setPageSize(1);
        result.setTotalPage(2);
        result.setList(Arrays.asList(1,2,3));
        return result;
    }
}
