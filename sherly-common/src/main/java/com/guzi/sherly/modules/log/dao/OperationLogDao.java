package com.guzi.sherly.modules.log.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.model.dto.OperationLogPageDTO;
import com.guzi.sherly.modules.log.mapper.OperationLogMapper;
import com.guzi.sherly.modules.log.model.OperationLog;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

/**
 * mybatis-plus Service 增强
 * @author 谷子毅
 * @date 2022/7/14
 */
@Service
public class OperationLogDao extends SherlyServiceImpl<OperationLogMapper, OperationLog> {
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
