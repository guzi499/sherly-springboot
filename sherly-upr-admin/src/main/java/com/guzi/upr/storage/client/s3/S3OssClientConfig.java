package com.guzi.upr.storage.client.s3;

import com.guzi.upr.model.admin.OssClientConfig;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/6/25
 */
@Data
public class S3OssClientConfig implements OssClientConfig {

    /** 节点地址 */
    private String endpoint;

    /** 域名 */
    private String domainName;

    /** 存储桶 */
    private String bucket;

    /** 访问Key */
    private String accessKey;

    /** 访问Secret */
    private String accessSecret;

    /** 地区 */
    private String region;

}
