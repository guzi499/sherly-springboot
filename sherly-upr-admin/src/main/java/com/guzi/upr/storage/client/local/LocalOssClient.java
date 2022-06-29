package com.guzi.upr.storage.client.local;

import cn.hutool.core.io.FileUtil;
import com.guzi.upr.storage.model.AbstractOssClient;

import java.io.File;

/**
 * @author 谷子毅
 * @date 2022/6/24
 */
public class LocalOssClient extends AbstractOssClient<LocalOssClientConfig> {

    public LocalOssClient(Long clientId, LocalOssClientConfig config) {
        super(clientId, config);
    }

    @Override
    public void init() {
        // 路径前缀补全
        config.setPrefix(System.getProperty("user.dir") + File.separator + config.getPrefix() + File.separator);
    }

    @Override
    public String upload(byte[] fileBytes, String path) {
        FileUtil.writeBytes(fileBytes, config.getPrefix() + path);
        return config.getDomainName() + "/api/oss/" + path;
    }

    @Override
    public void delete(String path) {
        FileUtil.del(config.getPrefix() + path);
    }

    @Override
    public byte[] download(String path) {
        return FileUtil.readBytes(config.getPrefix() + path);
    }

}
