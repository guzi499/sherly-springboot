package com.guzi.sherly.modules.quartz.model;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.guzi.sherly.common.util.SpringContextHolder;
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

import static com.guzi.sherly.modules.log.enums.LogTypeEnum.EXCEPTION_LOG;
import static com.guzi.sherly.modules.log.enums.LogTypeEnum.NORMAL_LOG;
import static com.guzi.sherly.modules.quartz.contants.ScheduleTaskConstants.SCHEDULE_TASK_PARAMS;

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
        if (exception != null) {
            exception.printStackTrace();
        }
        this.saveOne(duration, scheduleTask, records, exception);
    }

    private void saveOne(Long duration, ScheduleTask scheduleTask, List<String> records, Throwable exception) {
        ScheduleTaskLog scheduleTaskLog = new ScheduleTaskLog();
        scheduleTaskLog.setScheduleTaskId(scheduleTask.getScheduleTaskId());
        scheduleTaskLog.setScheduleTaskName(scheduleTask.getScheduleTaskName());
        scheduleTaskLog.setInvokeClassAndMethod(scheduleTask.getInvokeClassAndMethod());
        scheduleTaskLog.setInvokeParam(scheduleTask.getInvokeParam());
        scheduleTaskLog.setType(exception == null ? NORMAL_LOG.getType() : EXCEPTION_LOG.getType());
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
        String invokeParam = scheduleTask.getInvokeParam();

        List<String> split = StrUtil.split(invokeClassAndMethod, "-");
        String className = split.get(0);
        String methodName = split.get(1);

        Object bean;
        if (className.contains(".")) {
            bean = Class.forName(className).newInstance();
        } else {
            bean = SpringContextHolder.getBean(className);
        }

        if (StrUtil.isBlank(invokeParam)) {
            Method method = bean.getClass().getMethod(methodName);
            method.invoke(bean);
        } else {
            List<String> params = StrUtil.split(invokeParam, ",");

            Class<?>[] realClassType = new Class<?>[params.size()];
            Object[] realClassValue = new Object[params.size()];

            for (int i = 0; i < params.size(); i++) {
                String param = params.get(i);
                param = StrUtil.trimToEmpty(param);
                if (StrUtil.startWith(param, "'")) {
                    realClassType[i] = String.class;
                    realClassValue[i] = StrUtil.sub(param, 1, -1);
                } else if (Objects.equals("true", param) || Objects.equals("false", param)) {
                    realClassType[i] = Boolean.class;
                    realClassValue[i] = Boolean.valueOf(param);
                } else if (StrUtil.endWith(param, "D")) {
                    realClassType[i] = Double.class;
                    realClassValue[i] = Double.valueOf(StrUtil.sub(param, 0, -1));
                } else if (StrUtil.endWith(param, "L")) {
                    realClassType[i] = Long.class;
                    realClassValue[i] = Long.valueOf(StrUtil.sub(param, 0, -1));
                } else {
                    realClassType[i] = Integer.class;
                    realClassValue[i] = Integer.valueOf(StrUtil.sub(param, 0, -1));
                }
            }

            Method method = bean.getClass().getMethod(methodName, realClassType);
            method.invoke(bean, realClassValue);

        }
    }
}
