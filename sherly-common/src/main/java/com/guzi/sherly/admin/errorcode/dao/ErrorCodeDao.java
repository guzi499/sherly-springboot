package com.guzi.sherly.admin.errorcode.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.admin.errorcode.dto.ErrorCodePageDTO;
import com.guzi.sherly.admin.errorcode.mapper.ErrorCodeMapper;
import com.guzi.sherly.admin.errorcode.model.ErrorCodeDO;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

/**
 * @author 李仁杰
 * @date 2022/9/1
 */
@Service
public class ErrorCodeDao extends SherlyServiceImpl<ErrorCodeMapper, ErrorCodeDO> {

    /**
     * 根据错误代码查询错误数据
     * @param errorCode
     * @return
     */
    public ErrorCodeDO getByErrorCode(String errorCode) {
        LambdaQueryWrapper<ErrorCodeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ErrorCodeDO::getErrorCode, errorCode);
        return this.getOne(wrapper, false);
    }

    /**
     * 错误条件查询
     * @param dto
     * @return
     */
    public IPage<ErrorCodeDO> listPage(ErrorCodePageDTO dto) {
        SherlyLambdaQueryWrapper<ErrorCodeDO> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper
                .likeIfExist(ErrorCodeDO::getErrorCode, dto.getErrorCode())
                .likeIfExist(ErrorCodeDO::getMessage, dto.getMessage())
                .eqIfExist(ErrorCodeDO::getModuleId, dto.getModuleId());
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }
}
