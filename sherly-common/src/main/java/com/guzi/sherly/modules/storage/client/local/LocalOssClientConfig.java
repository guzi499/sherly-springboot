package com.guzi.sherly.modules.storage.client.local;

import com.guzi.sherly.modules.storage.model.OssClientConfig;
import lombok.Data;

/**
 * 本地对象存储客户端配置
 * （需要搭配 nginx使用）
 * @author 谷子毅
 * @date 2022/6/24
 */
@Data
public class LocalOssClientConfig implements OssClientConfig {

    /** 存储桶 */
    private String bucket;

    /** 访问域名 */
    private String domainName;
}
