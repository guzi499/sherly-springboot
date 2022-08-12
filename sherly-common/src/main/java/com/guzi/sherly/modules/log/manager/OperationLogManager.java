package com.guzi.sherly.modules.log.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.sherly.model.dto.OperationLogPageDTO;
import com.guzi.sherly.modules.log.mapper.OperationLogMapper;
import com.guzi.sherly.modules.log.model.OperationLog;
import com.guzi.sherly.util.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Service
public class OperationLogManager extends ServiceImpl<OperationLogMapper, OperationLog> {
    public Page<OperationLog> listPage(OperationLogPageDTO dto) {
        SherlyLambdaQueryWrapper<OperationLog> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper
                .eqIfExist(OperationLog::getType, dto.getType())
                .inIfExist(OperationLog::getCreateUserId, dto.getUserIds())
                .eqIfExist(OperationLog::getRequestMethod, dto.getRequestMethod())
                .likeIfExist(OperationLog::getUri, dto.getUri())
                .betweenIfExist(OperationLog::getDuration, dto.getBeginDuration(), dto.getEndDuration())
                .betweenIfExist(OperationLog::getCreateTime, dto.getBeginTime(), dto.getEndTime())
                .orderByDesc(OperationLog::getLogId);
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    public void removeAll() {
        this.getBaseMapper().removeAll();
    }
}
