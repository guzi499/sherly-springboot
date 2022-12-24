package com.guzi.sherly.modules.quartz.model;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.guzi.sherly.modules.quartz.dao.ScheduleTaskLogDao;
import com.guzi.sherly.modules.quartz.util.ScheduleTaskUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static com.guzi.sherly.model.contants.CommonConstants.EXCEPTION_LOG;
import static com.guzi.sherly.model.contants.CommonConstants.NORMAL_LOG;
import static com.guzi.sherly.modules.quartz.constants.ScheduleTaskConstants.SCHEDULE_TASK_PARAMS;

/**
 * @author 谷子毅
 * @date 2022/12/6
 */
@Slf4j
@DisallowConcurrentExecution
public class SherlyJob implements Job {

    @Resource
    private ScheduleTaskLogDao scheduleTaskLogDao;

    ThreadLocal<Long> recordTime = new ThreadLocal<>();

    public static ThreadLocal<List<String>> keyPointRecord = new ThreadLocal<>();


    @Override
    public void execute(JobExecutionContext context) {
        ScheduleTask scheduleTask = new ScheduleTask();
        Object object = context.getMergedJobDataMap().get(SCHEDULE_TASK_PARAMS);
        BeanUtils.copyProperties(object, scheduleTask);
        keyPointRecord.set(new ArrayList<>());
        List<String> records;
        try {
            before(context, scheduleTask);
            ScheduleTaskUtil.log("定时任务开始执行！");
            doExecute(context, scheduleTask);
            records = keyPointRecord.get();
            ScheduleTaskUtil.log("定时任务正常结束！");
            after(context, scheduleTask, records, null);
        } catch (Throwable exception) {
            ScheduleTaskUtil.log("定时任务异常终止！");
            records = keyPointRecord.get();
            after(context, scheduleTask, records, exception);
        } finally {
            recordTime.remove();
            keyPointRecord.remove();
        }
    }

    private void before(JobExecutionContext context, ScheduleTask scheduleTask) {
        recordTime.set(System.currentTimeMillis());
    }

    private void after(JobExecutionContext context, ScheduleTask scheduleTask, List<String> records, Throwable exception) {
        Long startTime = recordTime.get();
        Long duration = System.currentTimeMillis() - startTime;
        log.info("任务耗时{}ms", duration);
        log.info("任务执行步骤：");
        for (String record : records) {
            log.info(record);
        }
        this.saveOne(duration, scheduleTask, records, exception);
    }

    private void saveOne(Long duration, ScheduleTask scheduleTask, List<String> records, Throwable exception) {
        ScheduleTaskLog scheduleTaskLog = new ScheduleTaskLog();
        scheduleTaskLog.setScheduleTaskName(scheduleTask.getScheduleTaskName());
        scheduleTaskLog.setInvokeClassAndMethod(scheduleTask.getInvokeClassAndMethod());
        scheduleTaskLog.setInvokeParam(scheduleTask.getInvokeParam());
        scheduleTaskLog.setType(exception == null ? NORMAL_LOG : EXCEPTION_LOG);
        scheduleTaskLog.setDuration(duration);
        scheduleTaskLog.setKeyPointRecord(JSONUtil.toJsonStr(records));
        if (exception != null) {
            List<String> list = Arrays.stream(exception.getStackTrace()).map(Objects::toString).collect(Collectors.toList());
            list.add(0, exception.getMessage());
            scheduleTaskLog.setException(JSONUtil.toJsonStr(list));
        }
        scheduleTaskLog.setCreateTime(new Date(recordTime.get()));
        scheduleTaskLogDao.save(scheduleTaskLog);
    }

    /**
     * 真正的执行
     *
     * @param context
     * @param scheduleTask
     */
    @SneakyThrows
    protected void doExecute(JobExecutionContext context, ScheduleTask scheduleTask) {
        String invokeClassAndMethod = scheduleTask.getInvokeClassAndMethod();
        List<String> split = StrUtil.split(invokeClassAndMethod, "-");
        String className = split.get(0);
        String methodName = split.get(1);
        Object bean = Class.forName(className).newInstance();
        Method method = bean.getClass().getMethod(methodName);
        method.invoke(bean);
    }
}
