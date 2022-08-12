package com.guzi.sherly.storage.client.s3;

import cn.hutool.core.io.IoUtil;
import com.guzi.sherly.storage.model.AbstractOssClient;
import io.minio.*;
import io.minio.http.Method;

import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeUnit;

/**
 * S3对象存储客户端
 * @author 谷子毅
 * @date 2022/6/25
 */
public class S3OssClient extends AbstractOssClient<S3OssClientConfig> {

    private MinioClient client;

    public S3OssClient(Long clientId, S3OssClientConfig config) {
        super(clientId, config);
    }

    @Override
    public void init() {
        // 初始化客户端
        client = MinioClient.builder()
                .endpoint(config.getEndpoint())
                .region(config.getRegion())
                .credentials(config.getAccessKey(), config.getAccessSecret())
                .build();
    }

    @Override
    public void upload(byte[] fileBytes, String path) throws Exception {
        // 执行上传
        client.putObject(PutObjectArgs.builder()
                .bucket(config.getBucket())
                .object(path)
                .stream(new ByteArrayInputStream(fileBytes), fileBytes.length, -1)
                .build());
    }

    @Override
    public void delete(String path) throws Exception {
        client.removeObject(RemoveObjectArgs.builder()
                .bucket(config.getBucket())
                .object(path)
                .build());
    }

    @Override
    public byte[] download(String path) throws Exception {
        GetObjectResponse response = client.getObject(GetObjectArgs.builder()
                .bucket(config.getBucket())
                .object(path)
                .build());
        return IoUtil.readBytes(response);
    }

    @Override
    public String getAccessUrl(String path) throws Exception {
        return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .expiry(6, TimeUnit.HOURS)
                .bucket(config.getBucket())
                .object(path)
                .build());
    }
}
