package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.upr.model.BaseModel;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/3/17
 */
@Data
@TableName("sys_user")
public class User extends BaseModel {
    /** 用户id */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /** 账户用户id */
    private Long accountUserId;

    /** 昵称 */
    private String nickname;

    /** 姓名 */
    private String realName;

    /** 手机号 */
    private String phone;

    /** 用户头像 */
    private String avatar;

    /** 用户邮箱 */
    private String email;

    /** 性别[enum] */
    private Integer gender;

    /** 部门id */
    private Long departmentId;

    /** 启用禁用[enum] */
    private Integer enable;

    /** 最后登录时间 */
    private Date lastLoginTime;

    /** 最后登录ip */
    private String lastLoginIp;
}
