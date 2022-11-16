package com.guzi.sherly.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/11/16
 */
@Data
public class TenantPackageSelectVO {
    /** 租户套餐编号 */
    @ApiModelProperty("租户套餐编号")
    private Long tenantPackageId;

    /** 租户套餐名称 */
    @ApiModelProperty("租户套餐名称")
    private String tenantPackageName;
}
