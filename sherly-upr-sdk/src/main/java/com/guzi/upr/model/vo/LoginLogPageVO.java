package com.guzi.upr.model.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/7/15
 */
@Data
public class LoginLogPageVO {
    /** 日志id */
    @ApiModelProperty(value = "日志id")
    private Long logId;

    /** 登录账号 */
    @ApiModelProperty(value = "登录账号")
    private String username;

    /** 登录方式[enum] */
    @ApiModelProperty(value = "登录方式[enum]", allowableValues = "CommonConstants.java")
    private Integer type;

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

    /** 登录结果[enum] */
    @ApiModelProperty(value = "登录结果[enum]", allowableValues = "CommonConstants.java")
    private Integer result;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Data createTime;
}
