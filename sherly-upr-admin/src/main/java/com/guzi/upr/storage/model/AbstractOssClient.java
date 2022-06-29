package com.guzi.upr.storage.model;

import com.guzi.upr.model.admin.OssClientConfig;

import java.util.Objects;

/**
 * 对象存储服务客户端抽象
 * @author 谷子毅
 * @date 2022/6/24
 */
public abstract class AbstractOssClient<Config extends OssClientConfig> implements OssClient{
    /** 当前对象存储服务配置id */
    private final Long configId;

    /** 当前对象存储服务配置 */
    protected Config config;

    public AbstractOssClient(Long configId, Config config) {
        this.configId = configId;
        this.config = config;
    }

    /**
     * 客户端初始化
     */
    public abstract void init();

    /**
     * 更新客户端配置
     * @param config 新的客户端配置
     */
    void updateConfig(Config config) {
        if (Objects.equals(this.config, config)) {
            return;
        }
        this.config = config;
        this.init();
    }

    /**
     * 获取对象存储服务配置id
     * @return 对象存储服务配置id
     */
    @Override
    public Long getConfigId() {
        return configId;
    }
}
