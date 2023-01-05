package com.guzi.sherly.admin.tenant.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 谷子毅
 * @date 2022/11/15
 */
@Data
@TableName("ge_tenant_package_menu")
@EqualsAndHashCode(callSuper = true)
public class TenantPackageMenu extends BaseModel {
    /** 编号 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 租户套餐编号 */
    private Long tenantPackageId;

    /** 菜单编号 */
    private Long menuId;
}
