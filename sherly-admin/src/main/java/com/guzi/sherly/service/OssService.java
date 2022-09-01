package com.guzi.sherly.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.sherly.manager.OssFileManager;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.admin.OssFile;
import com.guzi.sherly.model.dto.OssFilePageDTO;
import com.guzi.sherly.model.exception.BizException;
import com.guzi.sherly.model.vo.OssFilePageVO;
import com.guzi.sherly.modules.storage.model.OssClient;
import com.guzi.sherly.util.OssUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
     * @param file
     * @param prefix
     */
    @SneakyThrows
    public String uploadOne(MultipartFile file, String prefix) {
        OssClient ossClient = ossUtil.getOssClient();
        if (ossClient == null) {
            throw new BizException(NO_OSS_CONFIG);
        }
        // 1. 获取文件后缀
        String fileName = file.getOriginalFilename();
        String suffix = FileUtil.getSuffix(fileName);

        // 2. 生成文件相对路径
        String relativePath = IdUtil.randomUUID() + "." + suffix;

        // 3. 如果path不为空，则只进行对象的保存
        if (StrUtil.isNotBlank(prefix)) {
            relativePath = prefix + relativePath;
            ossClient.upload(file.getBytes(), relativePath);
            return relativePath;
        }

        // 4. 如果path为空，则还需记录保存信息
        ossClient.upload(file.getBytes(), relativePath);
        OssFile ossFile = new OssFile();
        ossFile.setFileType(suffix);
        ossFile.setFileName(fileName);
        ossFile.setConfigId(ossClient.getConfigId());
        ossFile.setPath(relativePath);
        ossFile.setSize((int) file.getSize());
        ossFileManager.save(ossFile);

        return relativePath;
    }

    /**
     * 文件删除
     * @param fileId
     */
    @SneakyThrows
    public void removeOne(Long fileId) {
        ossFileManager.removeById(fileId);
    }

    /**
     * 文件下载
     * @param path
     * @return
     */
    @SneakyThrows
    public byte[] downloadOne(String path) {
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

        return PageResult.build(result, page.getTotal());

    }

    /**
     * 文件链接（如果是S3的话是带过期时间、带url参数签名认证的url）
     * @param path
     * @return
     */
    @SneakyThrows
    public String accessUrl(String path) {
        return ossUtil.accessUrl(path);
    }
}
