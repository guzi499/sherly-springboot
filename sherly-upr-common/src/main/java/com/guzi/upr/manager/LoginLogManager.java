package com.guzi.upr.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.log.model.LoginLog;
import com.guzi.upr.mapper.admin.LoginLogMapper;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Service
public class LoginLogManager extends ServiceImpl<LoginLogMapper, LoginLog> {
}
