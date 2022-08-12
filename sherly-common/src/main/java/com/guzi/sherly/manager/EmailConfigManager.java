package com.guzi.sherly.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.sherly.mapper.admin.EmailConfigMapper;
import com.guzi.sherly.model.admin.EmailConfig;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Service
public class EmailConfigManager extends ServiceImpl<EmailConfigMapper, EmailConfig> {
}
