package com.guzi.upr.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 谷子毅
 * @date 2022/5/15
 */
@Component
@Data
public class QiniuConfig {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.preUrl}")
    private String preUrl;

    @Value("${qiniu.bucket}")
    private String bucket;

}
