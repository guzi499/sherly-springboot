package com.guzi.sherly.modules.storage.client.local;

import cn.hutool.core.io.FileUtil;
import com.guzi.sherly.modules.storage.model.AbstractOssClient;

/**
 * 本地对象存储客户端
 * @author 谷子毅
 * @date 2022/6/24
 */
public class LocalOssClient extends AbstractOssClient<LocalOssClientConfig> {

    public LocalOssClient(Long clientId, LocalOssClientConfig config) {
        super(clientId, config);
    }

    @Override
    public void init() {
        // 真实存储地址设置为存储桶所在目录下
        config.setBucket(config.getBucket() + "/");
    }

    @Override
    public void upload(byte[] fileBytes, String path) {
        FileUtil.writeBytes(fileBytes, config.getBucket() + path);
    }

    @Override
    public void delete(String path) {
        FileUtil.del(config.getBucket() + path);
    }

    @Override
    public byte[] download(String path) {
        return FileUtil.readBytes(config.getBucket() + path);
    }

    @Override
    public String getAccessUrl(String path) {
        // 加" /sherly-oss/ " 的原因是为了使用 nginx做文件请求
        return config.getDomainName() + "/sherly-oss/" + path;
    }

}
