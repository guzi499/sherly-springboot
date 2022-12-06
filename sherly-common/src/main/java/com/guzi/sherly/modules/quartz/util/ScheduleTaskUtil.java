package com.guzi.sherly.modules.quartz.util;

import com.guzi.sherly.modules.quartz.model.ScheduleTask;
import com.guzi.sherly.modules.quartz.model.SherlyJob;
import lombok.SneakyThrows;
import org.quartz.*;

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
                .withIdentity(JobKey.jobKey("name" + scheduleTaskId))
                .build();

        CronTrigger cronTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity(TriggerKey.triggerKey("name" + scheduleTaskId))
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        jobDetail.getJobDataMap().put("params", scheduleTask);

        scheduler.scheduleJob(jobDetail, cronTrigger);

        scheduler.start();
    }
}
