package com.guzi.sherly.modules.log.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.model.dto.LoginLogPageDTO;
import com.guzi.sherly.modules.log.mapper.LoginLogMapper;
import com.guzi.sherly.modules.log.model.LoginLog;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Service
public class LoginLogDao extends SherlyServiceImpl<LoginLogMapper, LoginLog> {

    /**
     * 日志分页
     * @param dto
     * @return
     */
    public Page<LoginLog> listPage(LoginLogPageDTO dto) {
        SherlyLambdaQueryWrapper<LoginLog> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper
                .eqIfExist(LoginLog::getType, dto.getType())
                .eqIfExist(LoginLog::getResult, dto.getResult())
                .likeIfExist(LoginLog::getUsername, dto.getUsername())
                .betweenIfExist(LoginLog::getCreateTime, dto.getBeginTime(), dto.getEndTime())
                .orderByDesc(LoginLog::getLogId);
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    public void removeAll() {
        this.baseMapper.removeAll();
    }
}
