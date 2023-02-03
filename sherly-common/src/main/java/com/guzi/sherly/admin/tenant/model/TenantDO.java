package com.guzi.sherly.admin.tenant.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/3/18
 */
@Data
@TableName("ge_tenant")
@EqualsAndHashCode(callSuper = true)
public class TenantDO extends BaseModel {

    /** 租户编号 */
    @TableId(type = IdType.AUTO)
    private Long tenantId;

    /** 租户代码 */
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
