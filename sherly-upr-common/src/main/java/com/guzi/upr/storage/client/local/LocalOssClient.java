package com.guzi.upr.storage.client.local;

import cn.hutool.core.io.FileUtil;
import com.guzi.upr.storage.model.AbstractOssClient;

import java.io.File;

/**
 * 本地对象存储客户端
 * @author 谷子毅
 * @date 2022/6/24
 */
public class LocalOssClient extends AbstractOssClient<LocalOssClientConfig> {

    /** 真实的相对路径 */
    private String realPrefix;

    public LocalOssClient(Long clientId, LocalOssClientConfig config) {
        super(clientId, config);
    }

    @Override
    public void init() {
        // 真实存储地址设置为jar包所在目录
        realPrefix = System.getProperty("user.dir") + File.separator + config.getBucket() + File.separator;
        // 前缀补全，后面加个 "/"
        config.setBucket(config.getBucket() + "/");
    }

    @Override
    public void upload(byte[] fileBytes, String path) {
        FileUtil.writeBytes(fileBytes, realPrefix + path);
    }

    @Override
    public void delete(String path) {
        FileUtil.del(realPrefix + path);
    }

    @Override
    public byte[] download(String path) {
        return FileUtil.readBytes(realPrefix + path);
    }

    @Override
    public String getAccessUrl(String path) throws Exception {
        /*
            加" /sherly-oss/ " 的原因是为了使用 nginx，需要配置nginx访问 jar 包所在目录
            当访问前缀为 " http://xxx.xxx.xxx.xxx/api/oss/ " 开头的请求时，转到真实的文件地址
         */
        return config.getDomainName() + "/sherly-oss/" + config.getBucket() + path;
    }

}
