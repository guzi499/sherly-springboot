package com.guzi.sherly.modules.log.dto;

import cn.hutool.core.date.DatePattern;
import com.guzi.sherly.common.enums.LogTypeEnum;
import com.guzi.sherly.common.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/7/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OperationLogPageDTO extends PageQuery {
    /** 日志类型 */
    @ApiModelProperty(value = "日志类型")
    private LogTypeEnum type;

    /** 用户ids */
    @ApiModelProperty(value = "用户ids")
    private List<Long> userIds;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String realName;

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
