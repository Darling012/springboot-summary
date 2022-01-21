package com.learn.mvc.controller;

import com.learn.mvc.properties.BoyProperties;
import com.learn.mvc.properties.GirlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 廖师兄
 * 2016-10-30 23:36
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private GirlProperties girlProperties;

    @Autowired
    private BoyProperties boy;

    @GetMapping(value = "/say")
    public String say(@RequestParam(value = "id", required = false, defaultValue = "0") Integer myId) {
        return "id: " + myId;
//        return girlProperties.getCupSize();
    }
    @GetMapping(value = "/says/{id}")
    public String says(@PathVariable("id") String a,@RequestParam("b") String b) {
        return "a: " + a + "b" + b;
//        return girlProperties.getCupSize();
    }
    //http://localhost:8080/hello/1/bye/2
    @RequestMapping(value ="/{a}/bye/{id}", method = RequestMethod.POST)
    public String sayBye(@PathVariable("id") Integer id,@PathVariable("a") Integer a){
        //return boy.getName();
        return "id"+ id+a;

    }

    @Value("${women}")
    private  String women;
    //http://localhost:8080/hello/by?id=1
    @GetMapping(value="/by")
    public String bytbyb(@RequestParam(value = "id",required = false,defaultValue = "0") Integer myid){
     return "id " + myid + "第三方士大夫士大夫";
    }
}
