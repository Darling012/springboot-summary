package com.learn.mvc.timeCovert;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @program: girl
 * @version:
 * @description: 入参出参中日期格式化
 * @author: ling
 * @create: 2021-05-21 16:19
 **/
@RestController
@RequestMapping("admin/time")
public class TimeFormatController {
    /**
     * http://localhost:8080/admin/time/path/2020-01-01 11:11:11
     * 不需要加convert等 此方式可成功
     * @param time
     * @return
     */
    @GetMapping("path/{time}")
    public LocalDateTime testPathVariable(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime time) {
        //返回 2020-01-01T11:11:11
        return time;
    }

    /**
     *
     * http://localhost:8080/admin/time/request-param?time=2020-01-01 11:11:11
     * 不需要加convert等 此方式可成功
     * @param time
     * @return
     */
    @GetMapping("request-param")
    public LocalDateTime testRequestParam(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime time) {
        //返回 2020-01-01T11:11:11
        return time;
    }

    /**
     *  http://localhost:8080/admin/time/request-param-pojo?time=2020-01-01 11:11:11
     * 需要增加Converter才可以 或者pojo里的字段加@DateTimeFormat
     * DateHandlerConfig LocalDateTimeAdvice 都可以
     * @param time
     * @return
     */
    @GetMapping("request-param-pojo")
    public TimeParam testRequestParamPojo(TimeParam time) {
        //返回 2020-01-01T11:11:11
        return time;
    }
    @PostMapping("post/request-body-pojo")
    public TimeParam testPostRequestBody(@RequestBody  TimeParam time) {
        return time;
    }


    @Data
    public static class TimeParam {
        // 可解决 testRequestParamPojo
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime time;
    }


}
