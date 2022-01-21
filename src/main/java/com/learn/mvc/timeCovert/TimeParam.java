package com.learn.mvc.timeCovert;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeParam {
    // 可解决
    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
