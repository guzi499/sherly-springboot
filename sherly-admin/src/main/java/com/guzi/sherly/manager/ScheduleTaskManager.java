package com.guzi.sherly.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.dto.ScheduleTaskInsertDTO;
import com.guzi.sherly.model.dto.ScheduleTaskPageDTO;
import com.guzi.sherly.model.dto.ScheduleTaskUpdateDTO;
import com.guzi.sherly.model.vo.ScheduleTaskPageVO;
import com.guzi.sherly.modules.quartz.dao.ScheduleTaskDao;
import com.guzi.sherly.modules.quartz.model.ScheduleTask;
import com.guzi.sherly.modules.quartz.util.ScheduleTaskUtil;
import lombok.SneakyThrows;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.guzi.sherly.modules.quartz.constants.ScheduleTaskConstants.SCHEDULE_TASK_NAME;

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
        List<ScheduleTask> list = scheduleTaskDao.list();
        for (ScheduleTask scheduleTask : list) {
            ScheduleTaskUtil.createScheduleTaskJob(scheduler, scheduleTask);
        }
    }


    /**
     * 定时任务分页
     * @param dto
     * @return
     */
    public PageResult<ScheduleTaskPageVO> listPage(ScheduleTaskPageDTO dto) {
        Page<ScheduleTask> page = scheduleTaskDao.listPage(dto);

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
        ScheduleTask scheduleTask = new ScheduleTask();
        BeanUtils.copyProperties(dto, scheduleTask);
        scheduleTaskDao.save(scheduleTask);
        ScheduleTaskUtil.createScheduleTaskJob(scheduler, scheduleTask);
    }

    /**
     * 定时任务执行一次
     * @param scheduleTaskId
     */
    @SneakyThrows
    public void runOnce(Integer scheduleTaskId) {
        scheduler.triggerJob(JobKey.jobKey(SCHEDULE_TASK_NAME + scheduleTaskId));
    }

    /**
     * 定时任务删除
     * @param scheduleTaskId
     */
    @SneakyThrows
    public void removeOne(Integer scheduleTaskId) {
        scheduleTaskDao.removeById(scheduleTaskId);
        scheduler.deleteJob(JobKey.jobKey(SCHEDULE_TASK_NAME + scheduleTaskId));
    }

    /**
     * 定时任务更新
     * @param dto
     */
    @SneakyThrows
    public void updateOne(ScheduleTaskUpdateDTO dto) {
        ScheduleTask scheduleTask = new ScheduleTask();
        BeanUtils.copyProperties(dto, scheduleTask);
        scheduleTaskDao.updateById(scheduleTask);
        scheduler.deleteJob(JobKey.jobKey(SCHEDULE_TASK_NAME + dto.getScheduleTaskId()));
        ScheduleTaskUtil.createScheduleTaskJob(scheduler, scheduleTask);
    }
}
