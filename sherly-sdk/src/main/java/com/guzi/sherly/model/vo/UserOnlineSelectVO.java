package com.guzi.sherly.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/7/13
 */
@Data
public class UserOnlineSelectVO {
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
    private Date loginTime;

    /** 登录ip */
    @ApiModelProperty(value = "登录ip")
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

    /** 会话id */
    @ApiModelProperty(value = "会话id")
    private String sessionId;
}
