package com.guzi.upr.storage.client.local;

import com.guzi.upr.model.admin.OssClientConfig;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/6/24
 */
@Data
public class LocalOssClientConfig implements OssClientConfig {

    /** 本地绝对路径前缀
     *  - 格式如下：
     *      linux    -> "/root/nginx/html/"
     *      windows  -> "D://"
     */
    private String localPath;

    /** 远程访问域名
     *  - 格式如下：
     *      linux    -> "http://127.0.0.1/"
     *      windows  -> "D://"
     */
    private String domainName;
}
