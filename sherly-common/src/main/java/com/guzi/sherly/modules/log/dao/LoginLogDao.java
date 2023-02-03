package com.guzi.sherly.modules.log.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.modules.log.dto.LoginLogPageDTO;
import com.guzi.sherly.modules.log.mapper.LoginLogMapper;
import com.guzi.sherly.modules.log.model.LoginLogDO;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Service
public class LoginLogDao extends SherlyServiceImpl<LoginLogMapper, LoginLogDO> {

    /**
     * 日志分页
     * @param dto
     * @return
     */
    public Page<LoginLogDO> listPage(LoginLogPageDTO dto) {
        SherlyLambdaQueryWrapper<LoginLogDO> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper
                .eqIfExist(LoginLogDO::getType, dto.getType())
                .eqIfExist(LoginLogDO::getResult, dto.getResult())
                .likeIfExist(LoginLogDO::getUsername, dto.getUsername())
                .betweenIfExist(LoginLogDO::getCreateTime, dto.getBeginTime(), dto.getEndTime())
                .orderByDesc(LoginLogDO::getLogId);
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    public void removeAll() {
        this.baseMapper.removeAll();
    }
}
