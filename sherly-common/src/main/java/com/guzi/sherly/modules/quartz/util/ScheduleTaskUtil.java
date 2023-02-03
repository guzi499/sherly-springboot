package com.guzi.sherly.modules.quartz.util;

import com.guzi.sherly.modules.quartz.model.ScheduleTaskDO;
import com.guzi.sherly.modules.quartz.model.SherlyJob;
import lombok.SneakyThrows;
import org.quartz.*;

import java.util.List;

import static com.guzi.sherly.common.enums.UsableEnum.DISABLE;
import static com.guzi.sherly.modules.quartz.contants.ScheduleTaskConstants.SCHEDULE_TASK_NAME;
import static com.guzi.sherly.modules.quartz.contants.ScheduleTaskConstants.SCHEDULE_TASK_PARAMS;
import static com.guzi.sherly.modules.quartz.model.SherlyJob.keyPointRecord;

/**
 * @author 谷子毅
 * @date 2022/12/6
 */
public class ScheduleTaskUtil {

    /**
     * 创建定时任务实例
     * @param scheduler
     * @param scheduleTaskDO
     */
    @SneakyThrows
    public static void createScheduleTaskJob(Scheduler scheduler, ScheduleTaskDO scheduleTaskDO) {
        Class<SherlyJob> sherlyJobClass = SherlyJob.class;

        Integer scheduleTaskId = scheduleTaskDO.getScheduleTaskId();
        String cronExpression = scheduleTaskDO.getCronExpression();

        JobDetail jobDetail = JobBuilder
                .newJob(sherlyJobClass)
                .withIdentity(getJobKey(scheduleTaskId))
                .build();

        CronTrigger cronTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity(getTriggerKey(scheduleTaskId))
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        jobDetail.getJobDataMap().put(SCHEDULE_TASK_PARAMS, scheduleTaskDO);

        scheduler.scheduleJob(jobDetail, cronTrigger);

        if (scheduleTaskDO.getEnable() == DISABLE) {
            scheduler.pauseJob(getJobKey(scheduleTaskId));
        }
    }

    /**
     * 定时任务执行时的步骤记录
     * @param keyPoint
     */
    public static void log(String keyPoint) {
        List<String> list = keyPointRecord.get();
        list.add(keyPoint);
        keyPointRecord.set(list);
    }

    /**
     * 生成定时任务标识key
     * @param scheduleTaskId
     * @return
     */
    public static JobKey getJobKey(Integer scheduleTaskId) {
        return JobKey.jobKey(SCHEDULE_TASK_NAME + scheduleTaskId);
    }

    /**
     * 生成定时任务触发key
     * @param scheduleTaskId
     * @return
     */
    public static TriggerKey getTriggerKey(Integer scheduleTaskId) {
        return TriggerKey.triggerKey(SCHEDULE_TASK_NAME + scheduleTaskId);
    }

}
