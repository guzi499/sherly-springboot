package com.guzi.sherly.modules.quartz.dao;

import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.quartz.mapper.ScheduleTaskLogMapper;
import com.guzi.sherly.modules.quartz.model.ScheduleTaskLog;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/12/5
 */
@Service
public class ScheduleTaskLogDao extends SherlyServiceImpl<ScheduleTaskLogMapper, ScheduleTaskLog> {
}
