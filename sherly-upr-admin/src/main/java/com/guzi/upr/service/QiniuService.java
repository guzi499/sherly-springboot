package com.guzi.upr.service;

import com.guzi.upr.config.QiniuConfig;
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
 * @author 谷子毅
 * @date 2022/5/15
 */
@Service
public class QiniuService {

    @Autowired
    private QiniuConfig qiniuConfig;

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

    public String download(String fileName) throws UnsupportedEncodingException {
        String domainOfBucket = qiniuConfig.getPreUrl();
        String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");

        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);

        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());

        long expireInSeconds = 3600;

        return auth.privateDownloadUrl(publicUrl, expireInSeconds);
    }
}
