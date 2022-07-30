package com.guzi.upr.service;

import cn.hutool.core.io.FileTypeUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.upr.manager.OssFileManager;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.OssFile;
import com.guzi.upr.model.dto.OssFilePageDTO;
import com.guzi.upr.model.vo.OssFilePageVO;
import com.guzi.upr.storage.model.OssClient;
import com.guzi.upr.util.OssUtil;
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
    private OssUtil ossUtil;

    @Autowired
    private OssFileManager ossFileManager;

    /**
     * 文件上传
     * @param fileBytes
     * @param path
     * @throws Exception
     */
    public String uploadOne(byte[] fileBytes, String path) throws Exception {
        String newPath = System.currentTimeMillis() + "$" + path;

        String type = FileTypeUtil.getType(new ByteArrayInputStream(fileBytes));

        OssClient ossClient = ossUtil.getOssClient();
        ossClient.upload(fileBytes, newPath);

        OssFile ossFile = new OssFile();
        ossFile.setFileType(type);
        ossFile.setConfigId(ossClient.getConfigId());
        ossFile.setPath(newPath);
        ossFile.setSize(fileBytes.length);
        ossFileManager.save(ossFile);

        return newPath;
    }

    /**
     * 文件删除
     * @param fileId
     * @throws Exception
     */
    public void removeOne(Long fileId) throws Exception {
        ossFileManager.removeById(fileId);
    }

    /**
     * 文件下载
     * @param path
     * @return
     * @throws Exception
     */
    public byte[] downloadOne(String path) throws Exception {
        OssClient ossClient = ossUtil.getOssClient();
        return ossClient.download(path);
    }

    /**
     * 文件分页
     * @param dto
     * @return
     */
    public PageResult<OssFilePageVO> listPage(OssFilePageDTO dto) {
        IPage<OssFile> page = ossFileManager.listPage(dto);

        List<OssFilePageVO> result = page.getRecords().stream().map(e -> {
            OssFilePageVO ossFilePageVO = new OssFilePageVO();
            BeanUtils.copyProperties(e, ossFilePageVO);
            return ossFilePageVO;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());

    }

    /**
     * 文件链接（如果是S3的话是带过期时间、带url参数签名认证的url）
     * @param path
     * @return
     * @throws Exception
     */
    public String accessUrl(String path) throws Exception {
        return ossUtil.accessUrl(path);
    }
}
