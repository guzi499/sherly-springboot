package com.guzi.sherly.modules.quartz.util;

import com.guzi.sherly.modules.quartz.model.ScheduleTask;
import com.guzi.sherly.modules.quartz.model.SherlyJob;
import lombok.SneakyThrows;
import org.quartz.*;

import static com.guzi.sherly.modules.quartz.constants.ScheduleTaskConstants.SCHEDULE_TASK_NAME;
import static com.guzi.sherly.modules.quartz.constants.ScheduleTaskConstants.SCHEDULE_TASK_PARAMS;

/**
 * @author 谷子毅
 * @date 2022/12/6
 */
public class ScheduleTaskUtil {

    @SneakyThrows
    public static void createScheduleTaskJob(Scheduler scheduler, ScheduleTask scheduleTask) {
        Class<SherlyJob> sherlyJobClass = SherlyJob.class;

        Integer scheduleTaskId = scheduleTask.getScheduleTaskId();
        String cronExpression = scheduleTask.getCronExpression();

        JobDetail jobDetail = JobBuilder
                .newJob(sherlyJobClass)
                .withIdentity(JobKey.jobKey(SCHEDULE_TASK_NAME + scheduleTaskId))
                .build();

        CronTrigger cronTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity(TriggerKey.triggerKey(SCHEDULE_TASK_NAME + scheduleTaskId))
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        jobDetail.getJobDataMap().put(SCHEDULE_TASK_PARAMS, scheduleTask);

        scheduler.scheduleJob(jobDetail, cronTrigger);

        scheduler.pauseJob(JobKey.jobKey(SCHEDULE_TASK_NAME + scheduleTaskId));
    }
}
