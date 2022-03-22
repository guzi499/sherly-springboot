package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.upr.model.BaseModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/17
 */
@Data
@TableName("sys_user")
@ApiModel("user")
public class User extends BaseModel {
    /** id */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 昵称 */
    private String nickname;

    /** 姓名 */
    private String realName;

    /** 手机号 */
    private String phoneNumber;

    /** 密码 */
    private String password;

    /** 用户头像 */
    private String avatar;

    /** 用户邮箱 */
    private String email;

    /** 性别 */
    private String gender;

    /** 部门id */
    private Integer deptId;

    /** 0超级管理员 1管理员 2普通用户 */
    private Integer identity;

    /** 0不可用 1可用 */
    private Integer enable;

    /** 0未删除 1已删除 */
    private Integer delFlag;

    /** 最后登录时间 */
    private Date lastLoginTime;

    /** 最后登录IP */
    private String lastLoginIp;
}
