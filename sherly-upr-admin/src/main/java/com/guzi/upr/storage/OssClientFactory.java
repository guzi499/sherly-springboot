package com.guzi.upr.storage;

import cn.hutool.core.util.ReflectUtil;
import com.guzi.upr.model.admin.OssClientConfig;
import com.guzi.upr.security.util.SecurityUtil;
import com.guzi.upr.storage.enums.OssTypeEnum;
import com.guzi.upr.storage.model.AbstractOssClient;
import com.guzi.upr.storage.model.OssClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象存储服务客户端工厂
 * @author 谷子毅
 * @date 2022/6/27
 */
@Component
public class OssClientFactory {

    /**
     * 客户端容器
     * key: tenantCode
     * value: client
     */
    private final ConcurrentHashMap<String, AbstractOssClient<?>> clients = new ConcurrentHashMap<>();

    /**
     * 获取oss客户端对象
     * @return oss客户端对象
     */
    public OssClient getOssClient() {
        return clients.get(SecurityUtil.getTenantCode());
    }

    /**
     * 创建oss客户端
     * @param configId 配置id
     * @param type 存储方式
     * @param config oss客户端配置
     * @return 被创建的oss客户端对象
     */
    public AbstractOssClient createOssClient(Long configId, Integer type, OssClientConfig config) {
        OssTypeEnum ossTypeEnum = OssTypeEnum.getByType(type);
        AbstractOssClient client = (AbstractOssClient) ReflectUtil.newInstance(ossTypeEnum.getClientClass(), configId, config);
        client.init();
        clients.put(SecurityUtil.getTenantCode(), client);
        return client;
    }
}
