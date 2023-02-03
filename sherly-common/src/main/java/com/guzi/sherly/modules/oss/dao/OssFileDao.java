package com.guzi.sherly.modules.oss.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import com.guzi.sherly.modules.oss.dto.OssFilePageDTO;
import com.guzi.sherly.modules.oss.mapper.OssFileMapper;
import com.guzi.sherly.modules.oss.model.OssFileDO;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/6/29
 */
@Service
public class OssFileDao extends SherlyServiceImpl<OssFileMapper, OssFileDO> {
    /**
     * 文件分页
     * @param dto
     * @return
     */
    public IPage<OssFileDO> listPage(OssFilePageDTO dto) {
        SherlyLambdaQueryWrapper<OssFileDO> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper
                .eqIfExist(OssFileDO::getConfigId, dto.getConfigId())
                .likeIfExist(OssFileDO::getFileName, dto.getFileName())
                .likeIfExist(OssFileDO::getPath, dto.getPath())
                .betweenIfExist(OssFileDO::getCreateTime, dto.getBeginTime(), dto.getEndTime())
                .orderByDesc(OssFileDO::getFileId);
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }
}
