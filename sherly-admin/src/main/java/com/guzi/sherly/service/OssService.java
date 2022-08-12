package com.guzi.sherly.service;

import cn.hutool.core.io.FileTypeUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.sherly.manager.OssFileManager;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.admin.OssFile;
import com.guzi.sherly.model.dto.OssFilePageDTO;
import com.guzi.sherly.model.exception.BizException;
import com.guzi.sherly.model.vo.OssFilePageVO;
import com.guzi.sherly.modules.storage.model.OssClient;
import com.guzi.sherly.util.OssUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

import static com.guzi.sherly.model.exception.enums.AdminErrorEnum.NO_OSS_CONFIG;

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
        OssClient ossClient = ossUtil.getOssClient();
        if (ossClient == null) {
            throw new BizException(NO_OSS_CONFIG);
        }

        String newPath = System.currentTimeMillis() + "$" + path;
        String type = FileTypeUtil.getType(new ByteArrayInputStream(fileBytes));
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
        OssClient ossClient = ossUtil.getOssClient();
        if (ossClient == null) {
            return PageResult.buildEmpty();
        }
        dto.setConfigId(ossClient.getConfigId());
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
