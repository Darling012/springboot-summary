package com.learn.mvc.controller;

import com.learn.mvc.annotation.RestUrl;
import com.learn.mvc.copier.StudentCopier;
import com.learn.mvc.controller.pojo.StudentInfo;
import com.learn.mvc.domain.entities.Student;
import com.learn.mvc.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-01-04 17:38
 **/
@RestUrl("api/v1/student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    private final StudentService studentService;
    private final StudentCopier studentCopier;

    @PostMapping
    public void createStudent(@RequestBody Student student) {
        studentService.createStudent(student);
    }

    @GetMapping
    public List<Student> queryAll() {
        return studentService.queryAll();
    }

    @GetMapping("{id}")
    public StudentInfo queryById() {
        Student student = new Student();
        student.setId(1L);
        student.setName("张三");
        student.setAge(12);
        return studentCopier.entityToVo(student);
    }

}
