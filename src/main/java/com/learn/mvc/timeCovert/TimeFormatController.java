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
     * 不需要加convert等 此方式可成功
     * @param time
     * @return
     */
    @GetMapping("path/{time}")
    public LocalDateTime testPathVariable(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime time) {
        return time;
    }

    /**
     * 不需要加convert等 此方式可成功
     * @param time
     * @return
     */
    @GetMapping("request-param")
    public LocalDateTime testRequestParam(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime time) {
        return time;
    }

    /**
     * 需要增加Converter才可以
     * DateHandlerConfig LocalDateTimeAdvice 都可以
     * @param time
     * @return
     */
    @GetMapping("request-param-pojo")
    public TimeParam testRequestParamPojo(TimeParam time) {
        return time;
    }
    @PostMapping("post/request-body-pojo")
    public TimeParam testPostRequestBody(@RequestBody  TimeParam time) {
        return time;
    }


    @Data
    public static class TimeParam {
        // 可解决
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime time;
    }


}
