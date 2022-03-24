package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.upr.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/17
 */
@Data
@TableName("sys_user")
public class User extends BaseModel {
    /** 用户id */
    @ApiModelProperty("用户id")
    @TableId(type = IdType.AUTO)
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

    /** 密码 */
    @ApiModelProperty("密码")
    private String password;

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
}
