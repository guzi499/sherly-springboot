package com.guzi.upr.service;

import cn.hutool.core.io.FileTypeUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.upr.manager.OssConfigManager;
import com.guzi.upr.manager.OssFileManager;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.OssConfig;
import com.guzi.upr.model.admin.OssFile;
import com.guzi.upr.model.dto.OssFilePageDTO;
import com.guzi.upr.model.vo.OssFilePageVO;
import com.guzi.upr.storage.OssClientFactory;
import com.guzi.upr.storage.model.OssClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/6/26
 */
@Service
public class OssService {

    @Autowired
    private OssClientFactory ossClientFactory;

    @Autowired
    private OssConfigManager ossConfigManager;

    @Autowired
    private OssFileManager ossFileManager;

    private OssClient getOssClient() {
        OssClient ossClient = ossClientFactory.getOssClient();
        if (ossClient == null) {
            OssConfig ossConfig = ossConfigManager.getEnable();
            if (ossConfig == null) {
                System.out.println("没有可用的config");
            }
            return ossClientFactory.createOssClient(ossConfig.getConfigId(), ossConfig.getType(), ossConfig.getConfig());
        }
        return ossClient;
    }

    public void uploadOne(byte[] fileBytes, String path) throws Exception {
        String type = FileTypeUtil.getType(new ByteArrayInputStream(fileBytes));
        OssClient ossClient = getOssClient();
        ossClient.upload(fileBytes, path);

        OssFile ossFile = new OssFile();
        ossFile.setFileType(type);
        ossFile.setConfigId(ossClient.getConfigId());
        ossFile.setPath(path);
        ossFile.setSize(fileBytes.length);
        ossFile.setUrl(path);
        ossFileManager.save(ossFile);
    }

    public void removeOne(Long fileId) throws Exception {
        ossFileManager.removeById(fileId);
    }

    public byte[] downloadOne(String path) throws Exception {
        OssClient ossClient = getOssClient();
        return ossClient.download(path);
    }

    public PageResult listPage(OssFilePageDTO dto) {
        IPage<OssFile> page = ossFileManager.page(dto.pageInfo());

        List<OssFilePageVO> result = page.getRecords().stream().map(e -> {
            OssFilePageVO ossFilePageVO = new OssFilePageVO();
            BeanUtils.copyProperties(e, ossFilePageVO);
            return ossFilePageVO;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());

    }

    public String accessUrl(String path) throws Exception {
        OssClient ossClient = getOssClient();
        return ossClient.getAccessUrl(path);
    }
}
