package com.guzi.sherly.modules.storage.client.s3;

import cn.hutool.core.io.IoUtil;
import com.guzi.sherly.modules.storage.model.AbstractOssClient;
import io.minio.*;
import io.minio.http.Method;
import lombok.SneakyThrows;

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
    @SneakyThrows
    public void upload(byte[] fileBytes, String path) {
        // 执行上传
        client.putObject(PutObjectArgs.builder()
                .bucket(config.getBucket())
                .object(path)
                .stream(new ByteArrayInputStream(fileBytes), fileBytes.length, -1)
                .build());
    }

    @Override
    @SneakyThrows
    public void delete(String path) {
        client.removeObject(RemoveObjectArgs.builder()
                .bucket(config.getBucket())
                .object(path)
                .build());
    }

    @Override
    @SneakyThrows
    public byte[] download(String path) {
        GetObjectResponse response = client.getObject(GetObjectArgs.builder()
                .bucket(config.getBucket())
                .object(path)
                .build());
        return IoUtil.readBytes(response);
    }

    @Override
    @SneakyThrows
    public String getAccessUrl(String path) {
        return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .expiry(6, TimeUnit.HOURS)
                .bucket(config.getBucket())
                .object(path)
                .build());
    }
}
