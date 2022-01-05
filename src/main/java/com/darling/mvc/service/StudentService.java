package com.darling.mvc.service;

import com.darling.mvc.domain.entities.Student;

import java.util.List;

public interface StudentService {



    void createStudent(Student student);

    List<Student> queryAll();

    void deleteAll();
}
