package com.guzi.sherly.modules.quartz.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.model.dto.ScheduleTaskPageDTO;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import com.guzi.sherly.modules.quartz.mapper.ScheduleTaskMapper;
import com.guzi.sherly.modules.quartz.model.ScheduleTask;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/12/5
 */
@Service
public class ScheduleTaskDao extends SherlyServiceImpl<ScheduleTaskMapper, ScheduleTask> {
    /**
     * 定时任务分页
     * @param dto
     * @return
     */
    public Page<ScheduleTask> listPage(ScheduleTaskPageDTO dto) {
        SherlyLambdaQueryWrapper<ScheduleTask> wrapper = new SherlyLambdaQueryWrapper<>();
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    /**
     * 根据定时任务编号修改启用禁用
     * @param scheduleTaskId
     * @param enable
     */
    public void enableOne(Integer scheduleTaskId, Integer enable) {
        LambdaUpdateWrapper<ScheduleTask> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ScheduleTask::getScheduleTaskId, scheduleTaskId)
                .set(ScheduleTask::getEnable, enable);
        this.update(wrapper);
    }
}
