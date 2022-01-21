package com.learn.mvc.timeCovert;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface TimeFeignService {
    @PostMapping("post/request-body-pojo")
    TimeParam testPostRequestBody(@RequestBody TimeParam time);
}
