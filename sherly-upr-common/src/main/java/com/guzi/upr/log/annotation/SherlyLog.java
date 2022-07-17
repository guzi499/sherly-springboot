package com.guzi.upr.log.annotation;

import java.lang.annotation.*;

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
