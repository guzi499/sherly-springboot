package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/3/26
 */
@Data
public class PermissionUpdateDTO {

    /** 权限id */
    @ApiModelProperty("权限id")
    private Long permissionId;

    /** 权限名称 */
    @ApiModelProperty("权限名称")
    private String permissionName;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** 父权限id */
    @ApiModelProperty("父权限id")
    private Long parentId;
}
