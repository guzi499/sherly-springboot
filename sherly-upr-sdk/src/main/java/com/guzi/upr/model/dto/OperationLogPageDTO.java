package com.guzi.upr.model.dto;

import cn.hutool.core.date.DatePattern;
import com.guzi.upr.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/7/15
 */
@Data
public class OperationLogPageDTO extends PageQuery {
    /** 日志类型[enum] */
    @ApiModelProperty(value = "日志类型[enum]")
    private String type;

    /** 用户id */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /** 请求方式 */
    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    /** 请求uri */
    @ApiModelProperty(value = "请求uri")
    private String uri;

    /** 开始耗时 */
    @ApiModelProperty(value = "开始耗时")
    private Long beginDuration;

    /** 结束耗时 */
    @ApiModelProperty(value = "结束耗时")
    private Long endDuration;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date beginTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date endTime;
}
