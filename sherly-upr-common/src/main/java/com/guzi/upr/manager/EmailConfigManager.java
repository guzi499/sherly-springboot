package com.guzi.upr.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.EmailConfigMapper;
import com.guzi.upr.model.admin.EmailConfig;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Service
public class EmailConfigManager extends ServiceImpl<EmailConfigMapper, EmailConfig> {
}
