package com.guzi.sherly.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.sherly.mapper.ErrorCodeMapper;
import com.guzi.sherly.model.admin.ErrorCode;
import com.guzi.sherly.model.dto.ErrorCodePageDTO;
import com.guzi.sherly.util.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ErrorCodeManager extends ServiceImpl<ErrorCodeMapper, ErrorCode> {

    /**
     * 根据错误代码查询错误数据
     * @param errorCode
     * @return
     */
    public ErrorCode getErrorCode(String errorCode) {
        LambdaQueryWrapper<ErrorCode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ErrorCode::getErrorCode, errorCode);
        return this.getOne(wrapper, false);
    }

    /**
     * 根据模块id查询错误数据
     * @param moduleId
     * @return
     */
    public List<ErrorCode> getAll(Integer moduleId) {
        LambdaQueryWrapper<ErrorCode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ErrorCode::getModuleId, moduleId);
        return this.list(wrapper);
    }

    /**
     * 错误条件查询
     * @param dto
     * @return
     */
    public IPage<ErrorCode> listPage(ErrorCodePageDTO dto) {
        SherlyLambdaQueryWrapper<ErrorCode> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper
                .likeIfExist(ErrorCode::getErrorCode, dto.getErrorCode())
                .likeIfExist(ErrorCode::getMessage, dto.getMessage())
                .eqIfExist(ErrorCode::getErrorCode, dto.getErrorCode());
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }
}
