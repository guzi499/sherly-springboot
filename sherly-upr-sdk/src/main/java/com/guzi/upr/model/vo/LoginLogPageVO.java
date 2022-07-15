package com.guzi.upr.model.vo;

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

    /** 登录方式 */
    @ApiModelProperty(value = "登录方式")
    private Integer type;

    /** 请求ip */
    @ApiModelProperty(value = "请求ip")
    private String ip;

    /** 请求地址 */
    @ApiModelProperty(value = "请求地址")
    private String address;

    /** 请求浏览器 */
    @ApiModelProperty(value = "请求浏览器")
    private String browser;

    /** 登录结果 */
    @ApiModelProperty(value = "登录结果")
    private Integer result;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Data createTime;
}
