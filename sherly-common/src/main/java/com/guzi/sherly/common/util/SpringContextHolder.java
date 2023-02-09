package com.guzi.sherly.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author 谷子毅
 * @date 2022/7/22
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String className) {
        return (T) applicationContext.getBean(className);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getBeans(Class<? extends Annotation > annotationType) {
        return applicationContext.getBeansWithAnnotation(annotationType);
    }
}
