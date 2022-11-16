package com.guzi.sherly.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 谷子毅
 * @date 2022/11/15
 */
@Data
public class TenantPackageUpdateDTO {
    /** 租户套餐id */
    @ApiModelProperty(value = "租户套餐id")
    @NotNull
    private Long tenantPackageId;

    /** 租户套餐名称 */
    @ApiModelProperty(value = "租户套餐名称")
    @NotBlank
    private String tenantPackageName;

    /** 描述 */
    @ApiModelProperty(value = "描述")
    private String description;
}
