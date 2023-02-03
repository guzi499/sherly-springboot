package com.guzi.sherly.modules.quartz.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.common.enums.UsableEnum;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import com.guzi.sherly.modules.quartz.dto.ScheduleTaskPageDTO;
import com.guzi.sherly.modules.quartz.mapper.ScheduleTaskMapper;
import com.guzi.sherly.modules.quartz.model.ScheduleTaskDO;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/12/5
 */
@Service
public class ScheduleTaskDao extends SherlyServiceImpl<ScheduleTaskMapper, ScheduleTaskDO> {
    /**
     * 定时任务分页
     * @param dto
     * @return
     */
    public Page<ScheduleTaskDO> listPage(ScheduleTaskPageDTO dto) {
        SherlyLambdaQueryWrapper<ScheduleTaskDO> wrapper = new SherlyLambdaQueryWrapper<>();
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    /**
     * 根据定时任务编号修改可用性
     * @param scheduleTaskId
     * @param enable
     */
    public void enableOne(Integer scheduleTaskId, UsableEnum enable) {
        LambdaUpdateWrapper<ScheduleTaskDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ScheduleTaskDO::getScheduleTaskId, scheduleTaskId)
                .set(ScheduleTaskDO::getEnable, enable);
        this.update(wrapper);
    }
}
