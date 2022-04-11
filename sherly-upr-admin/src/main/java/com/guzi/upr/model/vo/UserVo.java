package com.guzi.upr.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author fudon
 * @version 1.0
 * @date 2022/4/9 0:57
 */
@Data
public class UserVo {
    /** 用户id */
    @ApiModelProperty("用户id")
    private Long userId;

    /** 昵称 */
    @ApiModelProperty("昵称")
    private String nickname;

    /** 姓名 */
    @ApiModelProperty("姓名")
    private String realName;

    /** 手机号 */
    @ApiModelProperty("手机号")
    private String phone;

    /** 用户头像 */
    @ApiModelProperty("用户头像")
    private String avater;

    /** 用户邮箱 */
    @ApiModelProperty("用户邮箱")
    private String email;

    /** 性别 */
    @ApiModelProperty("性别")
    private Integer gender;

    /** 部门id */
    @ApiModelProperty("部门id")
    private Long deptId;

    /** 0超级管理员 1管理员 2普通用户 */
    @ApiModelProperty("0超级管理员 1管理员 2普通用户")
    private Integer identity;

    /** 0不可用 1可用 */
    @ApiModelProperty("0不可用 1可用")
    private Integer enable;

    /** 最后登录时间 */
    @ApiModelProperty("最后登录时间")
    private Date lastLoginTime;

    /** 最后登录IP */
    @ApiModelProperty("最后登录IP")
    private String lastLoginIp;

    private List<Long> roleIds;
}
