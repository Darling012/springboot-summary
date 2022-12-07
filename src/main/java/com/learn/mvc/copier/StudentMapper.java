package com.learn.mvc.copier;

import com.learn.mvc.controller.pojo.StudentInfo;
import com.learn.mvc.domain.entities.Student;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface StudentMapper extends Converter<Student, StudentInfo> {

    StudentInfo entity2Info(Student student);
}
