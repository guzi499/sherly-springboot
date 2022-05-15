package com.guzi.upr.util;

import com.guzi.upr.config.QiniuConfig;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author 谷子毅
 * @date 2022/5/15
 */
@Component
public class QiniuUtil {

    @Autowired
    private QiniuConfig qiniuConfig;

    private static QiniuConfig config;

    @PostConstruct
    public void init() {
        config = this.qiniuConfig;
    }

    public static String getPrivateUrl(String fileName) {

        String domainOfBucket = config.getPreUrl();
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);

        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());

        long expireInSeconds = 3600;

        return auth.privateDownloadUrl(publicUrl, expireInSeconds);
    }
}
