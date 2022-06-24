package com.guzi.upr.storage.enums;

import com.guzi.upr.model.admin.OssClientConfig;
import com.guzi.upr.storage.client.local.LocalOssClient;
import com.guzi.upr.storage.client.local.LocalOssClientConfig;
import com.guzi.upr.storage.model.OssClient;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 对象存储枚举
 * @author 谷子毅
 * @date 2022/6/24
 */
@Getter
@AllArgsConstructor
public enum OssTypeEnum {
    /** 数据库 */
    DATABASE(1, null, null),
    /** 本地 */
    LOCAL(2, LocalOssClientConfig.class, LocalOssClient.class),
    /** FTP */
    FTP(3, null, null),
    /** SFTP */
    SFTP(4, null, null),
    /** S3规范 */
    S3(5, null, null),
    ;

    /** 存储类型 */
    private final Integer type;
    /** 配置类 */
    private final Class<? extends OssClientConfig> OssConfigClass;
    /** 客户端类 */
    private final Class<? extends OssClient> clientClass;

    public static OssTypeEnum getByType(Integer type) {
        return Arrays.stream(values()).filter(e -> e.type.equals(type)).findFirst().orElse(null);
    }
}
