package com.guzi.sherly.modules.quartz.util;

import com.guzi.sherly.modules.quartz.model.ScheduleTask;
import com.guzi.sherly.modules.quartz.model.SherlyJob;
import lombok.SneakyThrows;
import org.quartz.*;

import java.util.List;
import java.util.Objects;

import static com.guzi.sherly.model.contants.CommonConstants.DISABLE;
import static com.guzi.sherly.modules.quartz.constants.ScheduleTaskConstants.SCHEDULE_TASK_NAME;
import static com.guzi.sherly.modules.quartz.constants.ScheduleTaskConstants.SCHEDULE_TASK_PARAMS;
import static com.guzi.sherly.modules.quartz.model.SherlyJob.keyPointRecord;

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

        if (Objects.equals(scheduleTask.getEnable(), DISABLE)) {
            scheduler.pauseJob(JobKey.jobKey(SCHEDULE_TASK_NAME + scheduleTaskId));
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

}
