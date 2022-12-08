package com.learn.mvc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * <see>https://www.jianshu.com/p/5f6abd08ee08</see>
 */

@RestController
public class UrlController {
    @Value("${women}")
    private String women;

    // http://localhost:8080/a/aa
    @GetMapping(value = "/a/{a}")
    public String a(@PathVariable("a") String a) {
        return a;
    }

    @GetMapping(value = "/says/{id}")
    public String says(@PathVariable("id") String a, @RequestParam("b") String b) {
        return "a: " + a + "b" + b;
    }

    @GetMapping(value = "/{a}/{c}")
    public String says(@PathVariable("a") String a, @PathParam("c") String c, @RequestParam("b") String b) {
        // return "a: " + a +"/"+ "b:" + b +"/"+ "c:" + c;
        return a + b + c;
    }

    //http://localhost:8080/hello/1/bye/2
    @RequestMapping(value = "/{a}/bye/{id}", method = RequestMethod.POST)
    public String sayBye(@PathVariable("id") Integer id, @PathVariable("a") Integer a) {
        return "id" + id + a;

    }

    //@RequestParam 支持下面四种参数
    // defaultValue 如果本次请求没有携带这个参数，或者参数为空，那么就会启用默认值
    // name 绑定本次参数的名称，要跟URL上面的一样
    // required 这个参数是不是必须的
    // value 跟name一样的作用，是name属性的一个别名

    @GetMapping(value = "/c")
    public String c(@RequestParam("c") String c) {
        System.out.println(women);
        return c;
    }

    //http://localhost:8080/hello/say?id=1
    @GetMapping(value = "/say")
    public String say(@RequestParam(value = "id", required = false, defaultValue = "0") Integer myId) {
        return "id: " + myId;
    }
    //http://localhost:8080/c?c=ss

    //
    @GetMapping(value = "/b/{b}")
    public String b(@PathParam("b") String b) {
        return b;
    }


    //   post请求　body为formdata格式 多个参数 可以不写注解，名称匹配。params参数还是用@RequestParam。且params与formdata里数据名有重复，会成一个数组  TODO
    // post请求　body为formdata格式 可用@RequestParam获取参数，不写也能获取到
    //    https://www.cnblogs.com/acm-bingzi/p/spring_auto_box.html

}
