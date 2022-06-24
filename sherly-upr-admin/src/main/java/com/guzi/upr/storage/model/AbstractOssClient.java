package com.guzi.upr.storage.model;

import com.guzi.upr.model.admin.OssClientConfig;

import java.util.Objects;

/**
 * @author 谷子毅
 * @date 2022/6/24
 */
public abstract class AbstractOssClient<Config extends OssClientConfig> implements OssClient{

    private Long clientId;

    protected Config config;

    public AbstractOssClient(Long clientId, Config config) {
        this.clientId = clientId;
        this.config = config;
    }

    public abstract void init();

    void updateConfig(Config config) {
        if (Objects.equals(this.config, config)) {
            return;
        }
        this.config = config;
        this.init();
    }

    @Override
    public Long getClientId() {
        return clientId;
    }
}
