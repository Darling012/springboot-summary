package com.learn.mvc;

import com.learn.mvc.domain.entities.Student;
import com.learn.mvc.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-01-04 17:32
 **/
@Slf4j
@SpringBootTest
public class FlywayTest {
    @Autowired
    private StudentService studentService;

    @Test
    public void test() throws Exception {
        studentService.deleteAll();

        // 插入5个用户
        studentService.createStudent(new Student(1L,"Tom", 10));
        studentService.createStudent(new Student(2L,"Mike", 20));
        studentService.createStudent(new Student(3L,"Didispace", 30));
        studentService.createStudent(new Student(4L,"Oscar", 40));
        studentService.createStudent(new Student(5L,"Linda", 50));


    }
}
