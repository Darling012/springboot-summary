package com.learn.mvc.service;

import com.learn.mvc.domain.entities.Student;

import java.util.List;

public interface StudentService {



    void createStudent(Student student);

    List<Student> queryAll();

    void deleteAll();
}
