package com.guzi.upr.storage.client.local;

import cn.hutool.core.io.FileUtil;
import com.guzi.upr.storage.model.AbstractOssClient;

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

    }

    @Override
    public String upload(byte[] fileBytes, String path) {
        FileUtil.writeBytes(fileBytes,  config.getLocalPath() + "oss/" + path);
        return config.getDomainName() + "oss/" + path;
    }

    @Override
    public void delete(String path) {
        FileUtil.del(config.getLocalPath() + "oss/" + path);
    }

    @Override
    public byte[] download(String path) {
        return FileUtil.readBytes(config.getLocalPath() + "oss/" + path);
    }

}
