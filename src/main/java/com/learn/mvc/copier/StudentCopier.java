package com.learn.mvc.copier;

import com.learn.mvc.controller.pojo.StudentInfo;
import com.learn.mvc.annotation.BaseCopier;
import com.learn.mvc.domain.entities.Student;
import org.mapstruct.Mapper;

/**
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-01-18 14:09
 **/
@Mapper(componentModel = "spring")
public interface StudentCopier extends BaseCopier<Student,StudentInfo> {
}
