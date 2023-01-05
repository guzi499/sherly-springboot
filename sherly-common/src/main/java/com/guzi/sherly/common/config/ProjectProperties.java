package com.guzi.sherly.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * yml文件的项目属性配置
 * @author 谷子毅
 * @date 2022/7/22
 */
@Data
@Component
public class ProjectProperties {

    /** 项目名称 */
    @Value("${spring.application.name}")
    private String projectName;

}
