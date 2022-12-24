package com.guzi.sherly.modules.quartz.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/12/5
 */
@Data
@TableName("ge_schedule_task_log")
public class ScheduleTaskLog {
    /** 定时任务日志编号 */
    @TableId(type = IdType.AUTO)
    private Long scheduleTaskLogId;

    /** 定时任务名称 */
    private String scheduleTaskName;

    /** 调用类及方法 */
    private String invokeClassAndMethod;

    /** 调用参数 */
    private String invokeParam;

    /** 日志类型[enum] */
    private Integer type;

    /** 耗时 */
    private Long duration;

    /** 关键点记录 */
    private String keyPointRecord;

    /** 异常描述 */
    private String exception;

    /** 创建时间 */
    private Date createTime;
}
