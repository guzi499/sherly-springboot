package com.guzi.upr.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String avatar;

    /** 用户邮箱 */
    @ApiModelProperty("用户邮箱")
    private String email;

    /** 性别 */
    @ApiModelProperty("性别")
    private Integer gender;

    /** 部门id */
    @ApiModelProperty("部门id")
    private Long departmentId;

    /** 部门名称 */
    @ApiModelProperty("部门名称")
    private String departmentName;

    /** 0超级管理员 1管理员 2普通用户 */
    @ApiModelProperty("0超级管理员 1管理员 2普通用户")
    private Integer identity;

    /** 0不可用 1可用 */
    @ApiModelProperty("0不可用 1可用")
    private Integer enable;

    /** 最后登录时间 */
    @ApiModelProperty("最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;

    /** 最后登录IP */
    @ApiModelProperty("最后登录IP")
    private String lastLoginIp;

    /** 角色ids */
    @ApiModelProperty("角色ids")
    private List<Long> roleIds;
}
