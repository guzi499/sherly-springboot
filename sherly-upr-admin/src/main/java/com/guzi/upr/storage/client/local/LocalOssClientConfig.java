package com.guzi.upr.storage.client.local;

import com.guzi.upr.model.admin.OssClientConfig;
import lombok.Data;

/**
 * 本地对象存储配置
 * @author 谷子毅
 * @date 2022/6/24
 */
@Data
public class LocalOssClientConfig implements OssClientConfig {

    /**
     * 路径前缀（需要搭配 nginx使用）
     */
    private String prefix;

    /**
     * 访问域名
     */
    private String domainName;
}
