package com.guzi.sherly.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.model.BaseModel;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/3/18
 */
@Data
@TableName("sys_tenant")
public class Tenant extends BaseModel {

    /** 租户id */
    @TableId(type = IdType.AUTO)
    private Long tenantId;

    /** 租户code */
    private String tenantCode;

    /** 租户名称 */
    private String tenantName;

    /** 联系人 */
    private String contactUser;

    /** 联系电话 */
    private String contactPhone;

    /** 过期时间 */
    private Date expireTime;

    /** 用户上限 */
    private Long userLimit;
}
