package com.guzi.sherly.modules.log.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.modules.log.dto.OperationLogPageDTO;
import com.guzi.sherly.modules.log.mapper.OperationLogMapper;
import com.guzi.sherly.modules.log.model.OperationLogDO;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

/**
 * mybatis-plus Service 增强
 * @author 谷子毅
 * @date 2022/7/14
 */
@Service
public class OperationLogDao extends SherlyServiceImpl<OperationLogMapper, OperationLogDO> {
    public Page<OperationLogDO> listPage(OperationLogPageDTO dto) {
        SherlyLambdaQueryWrapper<OperationLogDO> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper
                .eqIfExist(OperationLogDO::getType, dto.getType())
                .inIfExist(OperationLogDO::getCreateUserId, dto.getUserIds())
                .eqIfExist(OperationLogDO::getRequestMethod, dto.getRequestMethod())
                .likeIfExist(OperationLogDO::getUri, dto.getUri())
                .betweenIfExist(OperationLogDO::getDuration, dto.getBeginDuration(), dto.getEndDuration())
                .betweenIfExist(OperationLogDO::getCreateTime, dto.getBeginTime(), dto.getEndTime())
                .orderByDesc(OperationLogDO::getLogId);
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    public void removeAll() {
        this.getBaseMapper().removeAll();
    }
}
