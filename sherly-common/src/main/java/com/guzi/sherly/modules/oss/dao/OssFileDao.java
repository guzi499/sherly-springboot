package com.guzi.sherly.modules.oss.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import com.guzi.sherly.modules.oss.dto.OssFilePageDTO;
import com.guzi.sherly.modules.oss.mapper.OssFileMapper;
import com.guzi.sherly.modules.oss.model.OssFile;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/6/29
 */
@Service
public class OssFileDao extends SherlyServiceImpl<OssFileMapper, OssFile> {
    /**
     * 文件分页
     * @param dto
     * @return
     */
    public IPage<OssFile> listPage(OssFilePageDTO dto) {
        SherlyLambdaQueryWrapper<OssFile> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper
                .eqIfExist(OssFile::getConfigId, dto.getConfigId())
                .likeIfExist(OssFile::getFileName, dto.getFileName())
                .likeIfExist(OssFile::getPath, dto.getPath())
                .betweenIfExist(OssFile::getCreateTime, dto.getBeginTime(), dto.getEndTime())
                .orderByDesc(OssFile::getFileId);
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }
}
