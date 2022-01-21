package com.learn.mvc.timeCovert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("feign/time")
public class FeignTimeController {
    @Autowired
    private TimeFeignService service;

     @PostMapping("post/request-body-pojo")
    public TimeParam testPostRequestBody(@RequestBody TimeParam time) {
        return service.testPostRequestBody(time);
    }
}
