package com.guzi.sherly.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 谷子毅
 * @date 2022/7/13
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SherlyLog {

    String description() default "";

    boolean noRecord() default false;

}
