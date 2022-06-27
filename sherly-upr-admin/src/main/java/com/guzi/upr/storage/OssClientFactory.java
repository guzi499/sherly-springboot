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
 * @author 谷子毅
 * @date 2022/6/27
 */
@Component
public class OssClientFactory {

    private final ConcurrentHashMap<String, OssClient> clients = new ConcurrentHashMap<>();

    public OssClient getOssClient() {
        return clients.get(SecurityUtil.getTenantCode());
    }

    public AbstractOssClient createOssClient(String tenantCode, Integer type, Long configId, OssClientConfig config) {
        OssTypeEnum ossTypeEnum = OssTypeEnum.getByType(type);
        AbstractOssClient ossClient = (AbstractOssClient) ReflectUtil.newInstance(ossTypeEnum.getClientClass(), configId, tenantCode, config);
        ossClient.init();
        clients.put(SecurityUtil.getTenantCode(), ossClient);
        return ossClient;
    }
}
