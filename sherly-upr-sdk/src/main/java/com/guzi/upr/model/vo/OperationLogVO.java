package com.guzi.upr.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/7/15
 */
@Data
public class OperationLogVO {
    /** 日志id */
    @ApiModelProperty(value = "日志id")
    private Long logId;

    /** 日志类型 */
    @ApiModelProperty(value = "日志类型")
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

    /** 请求参数 */
    @ApiModelProperty(value = "请求参数")
    private String requestParams;

    /** 请求ip */
    @ApiModelProperty(value = "请求ip")
    private String ip;

    /** 请求地址 */
    @ApiModelProperty(value = "请求地址")
    private String address;

    /** 请求浏览器 */
    @ApiModelProperty(value = "请求浏览器")
    private String browser;

    /** 耗时 */
    @ApiModelProperty(value = "耗时")
    private Long duration;

    /** 异常描述 */
    @ApiModelProperty(value = "异常描述")
    private String exception;
}
