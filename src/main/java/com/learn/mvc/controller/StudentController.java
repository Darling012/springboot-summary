package com.learn.mvc.controller;

import com.learn.mvc.domain.entities.Student;
import com.learn.mvc.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-01-04 17:38
 **/
@RestController
@RequestMapping("api/v1/student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public void createStudent(@RequestBody Student student){
        studentService.createStudent(student);
    }

    @GetMapping
    public List<Student> queryAll(){
      return   studentService.queryAll();
    }



}
