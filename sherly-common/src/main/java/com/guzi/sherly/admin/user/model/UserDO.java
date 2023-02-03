package com.guzi.sherly.admin.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.admin.user.enums.GenderEnum;
import com.guzi.sherly.common.enums.UsableEnum;
import com.guzi.sherly.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/3/17
 */
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class UserDO extends BaseModel {
    /** 用户编号 */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /** 账户用户编号 */
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

    /** 性别 */
    private GenderEnum gender;

    /** 部门编号 */
    private Long departmentId;

    /** 可用性 */
    private UsableEnum enable;

    /** 最后登录时间 */
    private Date lastLoginTime;

    /** 最后登录ip */
    private String lastLoginIp;
}
