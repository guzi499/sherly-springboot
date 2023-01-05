package com.guzi.sherly.admin.accountuser.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/4/27
 */
@Data
@TableName("ge_account_user")
public class AccountUser {

    /** 账户用户编号 */
    @TableId(type = IdType.AUTO)
    private Long accountUserId;

    /** 手机号 */
    private String phone;

    /** 密码 */
    private String password;

    /** 租户信息 */
    private String tenantData;

    /** 最后登录租户代码 */
    private String lastLoginTenantCode;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
