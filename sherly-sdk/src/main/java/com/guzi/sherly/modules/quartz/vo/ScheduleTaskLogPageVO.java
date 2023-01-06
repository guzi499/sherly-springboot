package com.guzi.sherly.modules.quartz.vo;

import com.guzi.sherly.modules.quartz.enums.ScheduleTaskLogTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/12/27
 */
@Data
public class ScheduleTaskLogPageVO {
    /** 定时任务日志编号 */
    @ApiModelProperty(value = "定时任务日志编号")
    private Long scheduleTaskLogId;

    /** 调用类及方法 */
    @ApiModelProperty(value = "调用类及方法")
    private String invokeClassAndMethod;

    /** 调用参数 */
    @ApiModelProperty(value = "调用参数")
    private String invokeParam;

    /** 日志类型{@link ScheduleTaskLogTypeEnum} */
    @ApiModelProperty(value = "日志类型[enum]")
    private Integer type;

    /** 耗时 */
    @ApiModelProperty(value = "耗时")
    private Long duration;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
