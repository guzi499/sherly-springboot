package com.guzi.upr.util;

import com.guzi.upr.config.ProjectProperties;
import com.guzi.upr.config.SherlyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 * springboot全局配置工具
 * @author 谷子毅
 * @date 2022/3/23
 */
public class GlobalPropertiesUtil {

    public final static ProjectProperties PROJECT_PROPERTIES = SpringContextHolder.getBean(ProjectProperties.class);

    public final static SherlyProperties SHERLY_PROPERTIES = SpringContextHolder.getBean(SherlyProperties.class);

}
