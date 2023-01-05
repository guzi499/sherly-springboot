package com.guzi.sherly.modules.oss.model;

/**
 * 对象存储服务客户端接口
 * @author 谷子毅
 * @date 2022/6/24
 */
public interface OssClient {
    /**
     * 获取对象存储服务配置id
     * @return 对象存储服务配置id
     */
    Long getConfigId();

    /**
     * 上传文件
     * @param fileBytes 文件的字节流
     * @param path 相对路径
     */
    void upload(byte[] fileBytes, String path);

    /**
     * 删除文件
     * @param path 相对路径
     */
    void delete(String path);

    /**
     * 下载文件
     * @param path 相对路径
     * @return 文件的字节流
     */
    byte[] download(String path);

    /**
     * 获取访问url
     * @param path 相对路径
     * @return 访问url
     */
    String getAccessUrl(String path);
}
