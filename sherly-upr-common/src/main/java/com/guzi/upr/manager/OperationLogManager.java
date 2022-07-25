package com.guzi.upr.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.log.model.OperationLog;
import com.guzi.upr.mapper.admin.OperationLogMapper;
import com.guzi.upr.model.dto.OperationLogPageDTO;
import com.guzi.upr.util.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Service
public class OperationLogManager extends ServiceImpl<OperationLogMapper, OperationLog> {
    public Page<OperationLog> listPage(OperationLogPageDTO dto) {
        SherlyLambdaQueryWrapper<OperationLog> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.eqIfExist(OperationLog::getType, dto.getType())
                .eqIfExist(OperationLog::getCreateUserId, dto.getUserId())
                .orderByDesc(OperationLog::getLogId);
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    public void removeAll() {
        this.getBaseMapper().removeAll();
    }
}
