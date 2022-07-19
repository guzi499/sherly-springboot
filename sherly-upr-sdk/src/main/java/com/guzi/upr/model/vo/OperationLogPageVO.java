package com.guzi.upr.model.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/7/15
 */
@Data
public class OperationLogPageVO {
    /** 日志id */
    @ApiModelProperty(value = "日志id")
    private Long logId;

    /** 日志类型[enum] */
    @ApiModelProperty(value = "日志类型[enum]", allowableValues = "CommonConstants.java")
    private String type;

    /** 描述 */
    @ApiModelProperty(value = "描述")
    private String description;

    /** 请求方式 */
    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    /** 请求uri */
    @ApiModelProperty(value = "请求uri")
    private String uri;

    /** 请求ip */
    @ApiModelProperty(value = "请求ip")
    private String ip;

    /** 请求地址 */
    @ApiModelProperty(value = "请求地址")
    private String address;

    /** 请求系统 */
    @ApiModelProperty(value = "请求系统")
    private String os;

    /** 请求浏览器 */
    @ApiModelProperty(value = "请求浏览器")
    private String browser;

    /** 耗时 */
    @ApiModelProperty(value = "耗时")
    private Long duration;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date createTime;


}
