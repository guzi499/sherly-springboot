package com.guzi.sherly.modules.quartz.model;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/12/6
 */
@Slf4j
@DisallowConcurrentExecution
public class SherlyJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        ScheduleTask scheduleTask = new ScheduleTask();
        Object object = jobExecutionContext.getMergedJobDataMap().get("params");
        BeanUtils.copyProperties(object, scheduleTask);
        doExecute(jobExecutionContext, scheduleTask);
        log.info("我执行了");
    }

    /**
     * 真正的执行
     *
     * @param jobExecutionContext
     * @param scheduleTask
     */
    @SneakyThrows
    protected void doExecute(JobExecutionContext jobExecutionContext, ScheduleTask scheduleTask) {
        String invokeClassAndMethod = scheduleTask.getInvokeClassAndMethod();
        List<String> split = StrUtil.split(invokeClassAndMethod, "-");
        String className = split.get(0);
        String methodName = split.get(1);
        Object bean = Class.forName(className).newInstance();
        Method method = bean.getClass().getMethod(methodName);
        method.invoke(bean);
    }
}
