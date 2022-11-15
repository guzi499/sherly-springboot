package com.guzi.sherly.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/11/16
 */
@Data
public class TenantPackageMenuUpdateDTO {

    /** 租户套餐id */
    @ApiModelProperty(value = "租户套餐id")
    private Long tenantPackageId;

    /** 菜单ids */
    @ApiModelProperty(value = "菜单ids")
    private List<Long> menuIds;



}
