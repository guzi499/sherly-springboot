package com.guzi.sherly.util;

import com.guzi.sherly.config.ProjectProperties;
import com.guzi.sherly.config.SherlyProperties;

/**
 * springboot全局配置工具
 * @author 谷子毅
 * @date 2022/3/23
 */
public class GlobalPropertiesUtil {

    public final static ProjectProperties PROJECT_PROPERTIES = SpringContextHolder.getBean(ProjectProperties.class);

    public final static SherlyProperties SHERLY_PROPERTIES = SpringContextHolder.getBean(SherlyProperties.class);

}
