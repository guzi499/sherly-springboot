package com.guzi.sherly.modules.quartz.dao;

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
    public Page<ScheduleTask> listPage(ScheduleTaskPageDTO dto) {
        SherlyLambdaQueryWrapper<ScheduleTask> wrapper = new SherlyLambdaQueryWrapper<>();
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }
}
