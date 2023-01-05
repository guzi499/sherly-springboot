package com.guzi.sherly.model.dto;

import com.guzi.sherly.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 谷子毅
 * @date 2022/12/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleTaskLogPageDTO extends PageQuery {
    /** 定时任务编号 */
    @ApiModelProperty(value = "定时任务编号")
    private Integer scheduleTaskId;
}
