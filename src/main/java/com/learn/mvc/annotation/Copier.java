package com.learn.mvc.annotation;

import org.mapstruct.Mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Darling
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Mapper(componentModel = "spring")
public @interface Copier {
     String componentModel() default "spring";
}
