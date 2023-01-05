package com.guzi.sherly.manager;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.sherly.common.exception.BizException;
import com.guzi.sherly.common.model.PageResult;
import com.guzi.sherly.common.util.GlobalPropertiesUtil;
import com.guzi.sherly.modules.oss.OssClientFactory;
import com.guzi.sherly.modules.oss.dao.OssConfigDao;
import com.guzi.sherly.modules.oss.dao.OssFileDao;
import com.guzi.sherly.modules.oss.dto.OssFilePageDTO;
import com.guzi.sherly.modules.oss.model.OssClient;
import com.guzi.sherly.modules.oss.model.OssConfig;
import com.guzi.sherly.modules.oss.model.OssFile;
import com.guzi.sherly.modules.oss.vo.OssFilePageVO;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.guzi.sherly.common.exception.enums.AdminErrorEnum.NO_OSS_CONFIG;

/**
 * @author 谷子毅
 * @date 2022/6/26
 */
@Service
public class OssManager {

    @Resource
    private OssFileDao ossFileDao;

    @Resource
    private OssClientFactory ossClientFactory;

    @Resource
    private OssConfigDao ossConfigDao;

    /**
     * 文件上传
     * @param file
     * @param prefix
     */
    @SneakyThrows
    public String uploadOne(MultipartFile file, String prefix) {
        OssClient ossClient = this.getOssClient();
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
        ossFileDao.save(ossFile);

        return relativePath;
    }

    /**
     * 文件删除
     * @param fileId
     */
    @SneakyThrows
    public void removeOne(Long fileId) {
        ossFileDao.removeById(fileId);
    }

    /**
     * 文件下载
     * @param path
     * @return
     */
    @SneakyThrows
    public byte[] downloadOne(String path) {
        OssClient ossClient = this.getOssClient();
        return ossClient.download(path);
    }

    /**
     * 文件分页
     * @param dto
     * @return
     */
    public PageResult<OssFilePageVO> listPage(OssFilePageDTO dto) {
        OssClient ossClient = this.getOssClient();
        if (ossClient == null) {
            return PageResult.buildEmpty();
        }
        dto.setConfigId(ossClient.getConfigId());
        IPage<OssFile> page = ossFileDao.listPage(dto);

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

    /**
     * 获取对象存储服务客户端
     * @return
     */
    private OssClient getOssClient() {
        OssClient ossClient = ossClientFactory.getOssClient(SecurityUtil.getTenantCode());
        if (ossClient == null) {
            OssConfig ossConfig = ossConfigDao.getEnable();
            if (ossConfig == null) {
                return null;
            }
            return ossClientFactory.createOssClient(SecurityUtil.getTenantCode(), ossConfig.getConfigId(), ossConfig.getType(), ossConfig.getConfig());
        }
        return ossClient;
    }

    /**
     * 文件链接（如果是S3的话是带过期时间、带url参数签名认证的url）
     * @param path
     * @return
     */
    @SneakyThrows
    public String accessUrl(String path) {
        if (StrUtil.isBlank(path)) {
            return GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultAvatar();
        }
        OssClient ossClient = this.getOssClient();
        return ossClient.getAccessUrl(path);
    }
}
