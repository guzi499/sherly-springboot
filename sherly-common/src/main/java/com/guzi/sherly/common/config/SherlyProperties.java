package com.guzi.sherly.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * yml文件的框架属性配置
 * @author 谷子毅
 * @date 2022/7/22
 */
@Data
@Component
@ConfigurationProperties(prefix = "sherly")
public class SherlyProperties {

    /** 开发环境标识 */
    private String devFlag;

    /** 演示环境标识 */
    private String demoEnv;

    /** 默认数据库 */
    private String defaultDb;

    /** 公共数据表 */
    private List<String> commonDbs;

    /** 默认密码 */
    private String defaultPassword;

    /** 默认头像 */
    private String defaultAvatar;

}
