package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.OssFileMapper;
import com.guzi.upr.model.admin.OssFile;
import com.guzi.upr.model.dto.OssFilePageDTO;
import com.guzi.upr.util.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/6/29
 */
@Service
public class OssFileManager extends ServiceImpl<OssFileMapper, OssFile> {
    /**
     * 文件分页
     * @param dto
     * @return
     */
    public IPage<OssFile> listPage(OssFilePageDTO dto) {
        SherlyLambdaQueryWrapper<OssFile> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.orderByDesc(OssFile::getFileId);
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }
}
