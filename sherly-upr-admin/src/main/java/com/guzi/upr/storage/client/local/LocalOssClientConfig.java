package com.guzi.upr.storage.client.local;

import com.guzi.upr.model.admin.OssClientConfig;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/6/24
 */
@Data
public class LocalOssClientConfig implements OssClientConfig {

    /** 前缀路径 */
    private String prefixPath;

    /** 域名 */
    private String domainName;
}
