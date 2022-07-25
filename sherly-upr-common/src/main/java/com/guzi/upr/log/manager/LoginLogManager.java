package com.guzi.upr.log.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.log.model.LoginLog;
import com.guzi.upr.log.mapper.LoginLogMapper;
import com.guzi.upr.model.dto.LoginLogPageDTO;
import com.guzi.upr.util.SherlyLambdaQueryWrapper;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Service
public class LoginLogManager extends ServiceImpl<LoginLogMapper, LoginLog> {

    public Page<LoginLog> listPage(LoginLogPageDTO dto) {
        SherlyLambdaQueryWrapper<LoginLog> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.orderByDesc(LoginLog::getLogId);
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    public void removeAll() {
        this.baseMapper.removeAll();
    }
}
