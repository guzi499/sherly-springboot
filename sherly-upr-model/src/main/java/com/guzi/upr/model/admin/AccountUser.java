package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/4/27
 */
@Data
@TableName("ge_account_user")
public class AccountUser {

    /** 账户用户id */
    @ApiModelProperty("账户用户id")
    @TableId(type = IdType.AUTO)
    private Long accountUserId;

    /** 手机号 */
    @ApiModelProperty("手机号")
    private String phone;

    /** 租户信息 */
    @ApiModelProperty("租户信息")
    private String tenantData;

    /** 最后登录租户代码 */
    @ApiModelProperty("最后登录租户代码")
    private String lastLoginTenantCode;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
