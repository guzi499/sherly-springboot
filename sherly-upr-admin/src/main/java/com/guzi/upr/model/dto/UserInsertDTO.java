package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author fudon
 * @version 1.0
 * @date 2022/3/27 21:51
 */
@Data
public class UserInsertDTO {
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String realName;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String avater;

    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String email;

    /**性别*/
    @ApiModelProperty("性别")
    private Integer gender;
    /**部门id*/
    @ApiModelProperty("部门id")
    private Long deptId;
    /**0超级管理员 1管理员 2普通用户*/
    @ApiModelProperty("0超级管理员 1管理员 2普通用户")
    private Integer identity;
    /**角色id*/
    @ApiModelProperty("角色id")
    private List<Long> roleIds;


}
