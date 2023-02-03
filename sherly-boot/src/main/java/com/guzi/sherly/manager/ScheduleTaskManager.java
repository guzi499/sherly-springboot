package com.guzi.sherly.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.common.enums.UsableEnum;
import com.guzi.sherly.common.model.PageResult;
import com.guzi.sherly.modules.quartz.dao.ScheduleTaskDao;
import com.guzi.sherly.modules.quartz.dto.ScheduleTaskInsertDTO;
import com.guzi.sherly.modules.quartz.dto.ScheduleTaskPageDTO;
import com.guzi.sherly.modules.quartz.dto.ScheduleTaskUpdateDTO;
import com.guzi.sherly.modules.quartz.model.ScheduleTaskDO;
import com.guzi.sherly.modules.quartz.util.ScheduleTaskUtil;
import com.guzi.sherly.modules.quartz.vo.ScheduleTaskPageVO;
import lombok.SneakyThrows;
import org.quartz.Scheduler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.guzi.sherly.common.enums.UsableEnum.DISABLE;
import static com.guzi.sherly.common.enums.UsableEnum.ENABLE;
import static com.guzi.sherly.modules.quartz.util.ScheduleTaskUtil.getJobKey;

/**
 * @author 谷子毅
 * @date 2022/12/5
 */
@Service
public class ScheduleTaskManager {

    @Resource
    private ScheduleTaskDao scheduleTaskDao;

    @Resource
    private Scheduler scheduler;

    /**
     * 项目启动时，从数据库查出所有定时任务并初始化
     */
    @PostConstruct
    @SneakyThrows
    public void init() {
        List<ScheduleTaskDO> list = scheduleTaskDao.list();
        for (ScheduleTaskDO scheduleTaskDO : list) {
            ScheduleTaskUtil.createScheduleTaskJob(scheduler, scheduleTaskDO);
        }
    }


    /**
     * 定时任务分页
     * @param dto
     * @return
     */
    public PageResult<ScheduleTaskPageVO> listPage(ScheduleTaskPageDTO dto) {
        Page<ScheduleTaskDO> page = scheduleTaskDao.listPage(dto);

        List<ScheduleTaskPageVO> result = page.getRecords().stream().map(e -> {
            ScheduleTaskPageVO scheduleTaskPageVO = new ScheduleTaskPageVO();
            BeanUtils.copyProperties(e, scheduleTaskPageVO);
            return scheduleTaskPageVO;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getTotal());
    }

    /**
     * 定时任务新增
     * @param dto
     */
    public void saveOne(ScheduleTaskInsertDTO dto) {
        ScheduleTaskDO scheduleTaskDO = new ScheduleTaskDO();
        BeanUtils.copyProperties(dto, scheduleTaskDO);
        scheduleTaskDao.save(scheduleTaskDO);
        ScheduleTaskUtil.createScheduleTaskJob(scheduler, scheduleTaskDO);
    }

    /**
     * 定时任务执行一次
     * @param scheduleTaskId
     */
    @SneakyThrows
    public void runOnce(Integer scheduleTaskId) {
        scheduler.triggerJob(getJobKey(scheduleTaskId));
    }

    /**
     * 定时任务删除
     * @param scheduleTaskId
     */
    @SneakyThrows
    public void removeOne(Integer scheduleTaskId) {
        scheduleTaskDao.removeById(scheduleTaskId);
        scheduler.deleteJob(getJobKey(scheduleTaskId));
    }

    /**
     * 定时任务更新
     * @param dto
     */
    @SneakyThrows
    public void updateOne(ScheduleTaskUpdateDTO dto) {
        ScheduleTaskDO scheduleTaskDO = new ScheduleTaskDO();
        BeanUtils.copyProperties(dto, scheduleTaskDO);
        scheduleTaskDao.updateById(scheduleTaskDO);
        scheduler.deleteJob(getJobKey(dto.getScheduleTaskId()));
        ScheduleTaskUtil.createScheduleTaskJob(scheduler, scheduleTaskDO);
    }

    /**
     * 定时任务禁用/启用
     * @param scheduleTaskId
     * @param enable
     */
    @SneakyThrows
    public void enableOne(Integer scheduleTaskId, UsableEnum enable) {
        scheduleTaskDao.enableOne(scheduleTaskId, enable);
        if (enable == ENABLE) {
            scheduler.resumeJob(getJobKey(scheduleTaskId));
        } else if (enable == DISABLE) {
            scheduler.pauseJob(getJobKey(scheduleTaskId));
        }
    }
}
