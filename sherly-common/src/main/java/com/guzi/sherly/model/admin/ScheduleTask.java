package com.guzi.sherly.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.model.BaseModel;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/12/4
 */
@Data
@TableName("ge_schedule_task")
public class ScheduleTask extends BaseModel {

    /** 定时任务id */
    @TableId(type = IdType.AUTO)
    private Integer scheduleTaskId;

    /** 定时任务名称 */
    private Integer scheduleTaskName;

    /** 调用类 */
    private Integer invokeClass;

    /** 调用方法 */
    private Integer invokeMethod;

    /** 调用参数 */
    private Integer invokeParam;

    /** corn表达式 */
    private Integer cronExpression;

    /** 启用禁用[enum] */
    private Integer enable;

    /** 描述 */
    private String description;
}
