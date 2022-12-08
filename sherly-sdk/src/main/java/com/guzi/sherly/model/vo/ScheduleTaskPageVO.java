package com.guzi.sherly.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/12/6
 */
@Data
public class ScheduleTaskPageVO {
    /** 定时任务编号 */
    @ApiModelProperty(value = "定时任务编号")
    private Integer scheduleTaskId;

    /** 定时任务名称 */
    @ApiModelProperty(value = "定时任务名称")
    private String scheduleTaskName;

    /** 调用类及方法 */
    @ApiModelProperty(value = "调用类及方法")
    private String invokeClassAndMethod;

    /** 调用参数 */
    @ApiModelProperty(value = "调用参数")
    private String invokeParam;

    /** corn表达式 */
    @ApiModelProperty(value = "corn表达式")
    private String cronExpression;

    /** 启用禁用[enum] */
    @ApiModelProperty(value = "启用禁用[enum]")
    private Integer enable;

    /** 描述 */
    @ApiModelProperty(value = "描述")
    private String description;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
