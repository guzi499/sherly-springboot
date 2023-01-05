package com.guzi.sherly.admin.useronline.model;

import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/5/25
 */

@Data
public class UserOnline {

    /** 用户编号 */
    private Long userId;

    /** 手机号 */
    private String phone;

    /** 登录租户代码 */
    private String loginTenantCode;

    /** 昵称 */
    private String nickname;

    /** 姓名 */
    private String realName;

    /** 登录时间 */
    private Date loginTime;

    /** 登录ip */
    private String ip;

    /** 登录地址 */
    private String address;

    /** 登录系统 */
    private String os;

    /** 登录浏览器 */
    private String browser;
}
