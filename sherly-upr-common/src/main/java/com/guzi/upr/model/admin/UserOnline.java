package com.guzi.upr.model.admin;

import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/5/25
 */

@Data
public class UserOnline {

    /** 用户id */
    private Long userId;

    /** 手机号 */
    private String phone;

    /** 登录租户code */
    private String loginTenantCode;

    /** 昵称 */
    private String nickname;

    /** 姓名 */
    private String realName;

    /** 登录时间 */
    private Date loginTime;

    /** 登录IP */
    private String ip;

    /** 登录地址 */
    private String address;

    /** 登录系统 */
    private String os;

    /** 登录浏览器 */
    private String browser;
}
