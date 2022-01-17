package com.learn.mvc.service.impl;

import com.learn.mvc.domain.entities.Student;
import com.learn.mvc.domain.repository.StudentRepository;
import com.learn.mvc.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private  final StudentRepository repository;

    @Override
    public void createStudent(Student student) {
        repository.save(student);
    }

    @Override
    public List<Student> queryAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
