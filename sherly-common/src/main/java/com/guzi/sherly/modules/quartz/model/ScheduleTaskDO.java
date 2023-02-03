package com.guzi.sherly.modules.quartz.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.common.model.BaseModel;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/12/4
 */
@Data
@TableName("ge_schedule_task")
public class ScheduleTaskDO extends BaseModel {

    /** 定时任务编号 */
    @TableId(type = IdType.AUTO)
    private Integer scheduleTaskId;

    /** 定时任务名称 */
    private String scheduleTaskName;

    /** 调用类及方法 */
    private String invokeClassAndMethod;

    /** 调用参数 */
    private String invokeParam;

    /** corn表达式 */
    private String cronExpression;

    /** 启用禁用[enum] */
    private Integer enable;

    /** 描述 */
    private String description;
}
