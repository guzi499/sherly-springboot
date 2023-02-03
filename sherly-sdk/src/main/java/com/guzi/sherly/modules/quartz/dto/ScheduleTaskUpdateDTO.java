package com.guzi.sherly.modules.quartz.dto;

import com.guzi.sherly.common.enums.UsableEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/12/8
 */
@Data
public class ScheduleTaskUpdateDTO {

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

    /** 可用性 */
    @ApiModelProperty(value = "可用性")
    private UsableEnum enable;

    /** 描述 */
    @ApiModelProperty(value = "描述")
    private String description;
}
