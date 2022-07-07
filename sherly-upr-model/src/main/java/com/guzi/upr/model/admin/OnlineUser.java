package com.guzi.upr.model.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/5/25
 */

@Data
public class OnlineUser {

    /** 用户id */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /** 登录租户code */
    @ApiModelProperty(value = "登录租户code")
    private String loginTenantCode;

    /** 昵称 */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    /** 登录IP */
    @ApiModelProperty(value = "登录IP")
    private String ip;

    /** 登录地址 */
    @ApiModelProperty(value = "登录地址")
    private String address;

    /** 登录系统 */
    @ApiModelProperty(value = "登录系统")
    private String os;

    /** 登录浏览器 */
    @ApiModelProperty(value = "登录浏览器")
    private String browser;
}
