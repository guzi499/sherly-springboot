package com.guzi.upr.service;

import com.guzi.upr.manager.OssConfigManager;
import com.guzi.upr.model.admin.OssConfig;
import com.guzi.upr.security.util.SecurityUtil;
import com.guzi.upr.storage.OssClientFactory;
import com.guzi.upr.storage.model.OssClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/6/26
 */
@Service
public class OssService {

    @Autowired
    private OssClientFactory ossClientFactory;

    @Autowired
    private OssConfigManager ossConfigManager;

    private OssClient getOssClient() {
        OssClient ossClient = ossClientFactory.getOssClient();
        if (ossClient == null) {
            OssConfig ossConfig = ossConfigManager.getEnable();
            if (ossConfig == null) {
                System.out.println("没有可用的config");
            }
            return ossClientFactory.createOssClient(SecurityUtil.getTenantCode(), ossConfig.getType(), ossConfig.getConfigId(), ossConfig.getConfig());
        }
        return ossClient;
    }

    public String uploadOne(byte[] fileBytes, String path) throws Exception {
        OssClient ossClient = getOssClient();
        return ossClient.upload(fileBytes, path);
    }
}
