package com.learn.mvc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;


@RestController
public class UrlController {
    @Value("${women}")
    private String women;

    @GetMapping(value = "/{a}/{c}")
    public String says(@PathVariable("a") String a, @PathParam("c") String c, @RequestParam("b") String b ) {
        // return "a: " + a +"/"+ "b:" + b +"/"+ "c:" + c;
        return a + b + c;
    }


    //@RequestParam 支持下面四种参数
    // defaultValue 如果本次请求没有携带这个参数，或者参数为空，那么就会启用默认值
    // name 绑定本次参数的名称，要跟URL上面的一样
    // required 这个参数是不是必须的
    // value 跟name一样的作用，是name属性的一个别名

    // http://localhost:8080/a/aa
    @GetMapping(value = "/a/{a}")
    public String a(@PathVariable("a") String a) {
        // return "a: " + a +"/"+ "b:" + b +"/"+ "c:" + c;
        return a ;
    }
    //
    @GetMapping(value = "/b/{b}")
    public String b(@PathParam("b") String b) {
        return b ;
    }
    //http://localhost:8080/c?c=ss
    @GetMapping(value = "/c")
    public String c(@RequestParam("c") String c ) {
        System.out.println(women);
        return c;
    }
}
