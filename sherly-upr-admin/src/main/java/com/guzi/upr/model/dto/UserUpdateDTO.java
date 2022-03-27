package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fudon
 * @version 1.0
 * @date 2022/3/27 21:51
 */
@Data
public class UserUpdateDTO {
    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("昵称")
    private String nickname;


    @ApiModelProperty("姓名")
    private String realName;


    @ApiModelProperty("手机号")
    private String phone;


    @ApiModelProperty("密码")
    private String password;


    @ApiModelProperty("用户头像")
    private String avater;


    @ApiModelProperty("用户邮箱")
    private String email;


    @ApiModelProperty("性别")
    private Integer gender;


    @ApiModelProperty("部门id")
    private Long deptId;


    @ApiModelProperty("0超级管理员 1管理员 2普通用户")
    private Integer identity;

    @ApiModelProperty("0不可用 1可用")
    private Integer enable;

    @ApiModelProperty("角色id")
    private Long roleId;
}
