package com.guzi.upr.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.log.model.OperationLog;
import com.guzi.upr.mapper.admin.OperationLogMapper;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Service
public class OperationLogManager extends ServiceImpl<OperationLogMapper, OperationLog> {
}
