package com.darling.mvc.service.impl;

import com.darling.mvc.domain.entities.Student;
import com.darling.mvc.domain.repository.StudentRepository;
import com.darling.mvc.service.StudentService;
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
