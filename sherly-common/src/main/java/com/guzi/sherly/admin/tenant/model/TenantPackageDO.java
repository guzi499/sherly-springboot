package com.guzi.sherly.admin.tenant.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.common.enums.UsableEnum;
import com.guzi.sherly.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 谷子毅
 * @date 2022/11/15
 */
@Data
@TableName("ge_tenant_package")
@EqualsAndHashCode(callSuper = true)
public class TenantPackageDO extends BaseModel {

    /** 租户套餐编号 */
    @TableId(type = IdType.AUTO)
    private Long tenantPackageId;

    /** 租户套餐名称 */
    private String tenantPackageName;

    /** 描述 */
    private String description;

    /** 可用性 */
    private UsableEnum enable;
}
