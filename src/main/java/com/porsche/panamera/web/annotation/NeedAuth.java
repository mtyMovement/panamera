package com.porsche.panamera.web.annotation;/*
 @author maoty
 @DESCRIPTION ${DESCRIPTION}
 @create 2019/5/17 0017
*/

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedAuth {
    boolean required() default true;
}
