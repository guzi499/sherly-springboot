package com.guzi.upr.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 谷子毅
 * @date 2022/7/22
 */
@Data
@Component
@ConfigurationProperties(prefix = "sherly")
public class SherlyProperties {

    /** 开发环境标识 */
    private final Boolean devFlag;

    /** 默认密码 */
    private final String defaultPassword;

}
