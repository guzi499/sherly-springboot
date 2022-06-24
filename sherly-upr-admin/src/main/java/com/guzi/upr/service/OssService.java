package com.guzi.upr.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 对象存储服务
 * @author 谷子毅
 * @date 2022/6/24
 */
public interface OssService {

    /**
     * 文件上传
     * @param file
     * @return
     */
    String upload(MultipartFile file);

    /**
     * 文件下载
     * @param fileName
     * @return
     */
    String download(String fileName);
}
