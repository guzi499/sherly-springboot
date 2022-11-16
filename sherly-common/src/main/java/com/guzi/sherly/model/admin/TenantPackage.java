package com.guzi.sherly.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.model.BaseModel;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/11/15
 */
@Data
@TableName("ge_tenant_package")
public class TenantPackage extends BaseModel {

    /** 租户套餐id */
    @TableId(type = IdType.AUTO)
    private Long tenantPackageId;

    /** 租户套餐名称 */
    private String tenantPackageName;

    /** 描述 */
    private String description;

    /** 启用禁用[enum] */
    private Integer enable;
}
