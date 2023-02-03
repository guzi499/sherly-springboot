package com.guzi.sherly.modules.quartz.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import com.guzi.sherly.modules.quartz.dto.ScheduleTaskLogPageDTO;
import com.guzi.sherly.modules.quartz.mapper.ScheduleTaskLogMapper;
import com.guzi.sherly.modules.quartz.model.ScheduleTaskLogDO;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/12/5
 */
@Service
public class ScheduleTaskLogDao extends SherlyServiceImpl<ScheduleTaskLogMapper, ScheduleTaskLogDO> {
    public Page<ScheduleTaskLogDO> listPage(ScheduleTaskLogPageDTO dto) {
        SherlyLambdaQueryWrapper<ScheduleTaskLogDO> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.eqIfExist(ScheduleTaskLogDO::getScheduleTaskId, dto.getScheduleTaskId());
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }
}
