package com.learn.mvc.domain.repository;

import com.learn.mvc.domain.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: springboot-summary
 * @version:
 * @author: ling
 * @createTime: 2022-01-04 17:34
 **/
public interface StudentRepository extends JpaRepository<Student, Long> {
}
