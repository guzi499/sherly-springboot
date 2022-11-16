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
@TableName("ge_tenant_package_menu")
public class TenantPackageMenu extends BaseModel {
    /** 编号 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 租户套餐编号 */
    private Long tenantPackageId;

    /** 菜单编号 */
    private Long menuId;
}
