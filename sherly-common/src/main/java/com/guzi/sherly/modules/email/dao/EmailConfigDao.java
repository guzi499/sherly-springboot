package com.guzi.sherly.modules.email.dao;

import com.guzi.sherly.modules.email.mapper.EmailConfigMapper;
import com.guzi.sherly.modules.email.model.EmailConfig;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Service
public class EmailConfigDao extends SherlyServiceImpl<EmailConfigMapper, EmailConfig> {
}
