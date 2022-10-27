package com.guzi.sherly.util;

import cn.hutool.core.util.StrUtil;
import com.guzi.sherly.manager.OssConfigManager;
import com.guzi.sherly.model.admin.OssConfig;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import com.guzi.sherly.modules.storage.OssClientFactory;
import com.guzi.sherly.modules.storage.model.OssClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 谷子毅
 * @date 2022/7/8
 */
@Component
public class OssUtil {

    @Resource
    private OssClientFactory ossClientFactory;

    @Resource
    private OssConfigManager ossConfigManager;

    /**
     * 获取对象存储服务客户端
     * @return
     */
    public OssClient getOssClient() {
        OssClient ossClient = ossClientFactory.getOssClient(SecurityUtil.getTenantCode());
        if (ossClient == null) {
            OssConfig ossConfig = ossConfigManager.getEnable();
            if (ossConfig == null) {
                return null;
            }
            return ossClientFactory.createOssClient(SecurityUtil.getTenantCode(), ossConfig.getConfigId(), ossConfig.getType(), ossConfig.getConfig());
        }
        return ossClient;
    }

    /**
     * 文件链接（如果是S3的话是带过期时间、带url参数签名认证的url）
     * @param path
     * @return
     * @throws Exception
     */
    public String accessUrl(String path) throws Exception {
        if (StrUtil.isBlank(path)) {
            return GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultAvatar();
        }
        OssClient ossClient = this.getOssClient();
        return ossClient.getAccessUrl(path);
    }
}
