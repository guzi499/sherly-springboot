package com.guzi.sherly.modules.storage.enums;

import com.guzi.sherly.modules.storage.client.local.LocalOssClient;
import com.guzi.sherly.modules.storage.client.local.LocalOssClientConfig;
import com.guzi.sherly.modules.storage.client.s3.S3OssClient;
import com.guzi.sherly.modules.storage.client.s3.S3OssClientConfig;
import com.guzi.sherly.modules.storage.model.OssClient;
import com.guzi.sherly.modules.storage.model.OssClientConfig;
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
    S3(5, S3OssClientConfig.class, S3OssClient.class),
    ;

    /** 存储方式[enum] */
    private final Integer type;
    /** 配置类 */
    private final Class<? extends OssClientConfig> OssConfigClass;
    /** 客户端类 */
    private final Class<? extends OssClient> clientClass;

    public static OssTypeEnum getByType(Integer type) {
        return Arrays.stream(values()).filter(e -> e.type.equals(type)).findFirst().orElse(null);
    }
}
