package com.guzi.upr.service.impl;

import com.guzi.upr.config.QiniuConfig;
import com.guzi.upr.service.OssService;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 七牛云对象存储服务
 * @author 谷子毅
 * @date 2022/6/24
 */
@Service
public class QiniuServiceImpl implements OssService {

    @Autowired
    private QiniuConfig qiniuConfig;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file) {
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        String upToken = auth.uploadToken(qiniuConfig.getBucket());

        try {
            Response response = uploadManager.put(file.getBytes(), file.getOriginalFilename(), upToken);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return qiniuConfig.getBucket() + "/" + file.getOriginalFilename();
    }

    /**
     * 文件下载
     * @param fileName
     * @return
     */
    @Override
    public String download(String fileName) {
        String domainOfBucket = qiniuConfig.getPreUrl();
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);

        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());

        long expireInSeconds = 3600;

        return auth.privateDownloadUrl(publicUrl, expireInSeconds);
    }
}
