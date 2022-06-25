package com.guzi.upr.storage.model;

/**
 * 对象存储客户端
 * @author 谷子毅
 * @date 2022/6/24
 */
public interface OssClient {
    /**
     * 获取客户端编号
     * @return
     */
    Long getClientId();

    /**
     * 上传文件
     * @param fileBytes
     * @param path
     * @return
     */
    String upload(byte[] fileBytes, String path) throws Exception;

    /**
     * 删除文件
     * @param path
     */
    void delete(String path) throws Exception;

    /**
     * 下载文件
     *
     * @param path
     * @return
     */
    byte[] download(String path) throws Exception;
}
