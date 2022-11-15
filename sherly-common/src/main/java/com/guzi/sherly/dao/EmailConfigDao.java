package com.guzi.sherly.dao;

import com.guzi.sherly.mapper.EmailConfigMapper;
import com.guzi.sherly.model.admin.EmailConfig;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Service
public class EmailConfigDao extends SherlyServiceImpl<EmailConfigMapper, EmailConfig> {
}
