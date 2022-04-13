package com.guzi.upr.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * springboot全局配置工具
 * @author 谷子毅
 * @date 2022/3/23
 */
@Component
public class GlobalParamUtil {

    @Autowired
    private Environment environment;
    private static Environment env;

    @PostConstruct
    public void init() {
        env = this.environment;
    }

    public static String getValue(String key) {
        return env.getProperty(key);
    }
}
