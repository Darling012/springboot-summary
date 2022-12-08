package com.learn.mvc.copier;

import com.learn.mvc.controller.pojo.StudentInfo;
import com.learn.mvc.controller.pojo.UserInfo;
import com.learn.mvc.domain.entities.Student;
import org.apache.catalina.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
@Mapper(componentModel = "spring")
public interface UserMapper extends Converter<User, UserInfo> {

}
