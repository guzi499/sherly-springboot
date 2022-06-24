package com.guzi.upr.model.admin;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 对象存储客户端配置文件
 * @author 谷子毅
 * @date 2022/6/24
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface OssClientConfig {
}
