package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Data
public class PermissionInsertDTO {

    /** 权限名 */
    @ApiModelProperty("权限名")
    private String permissionName;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** 父权限id */
    @ApiModelProperty("父权限id")
    private Long parentId;

}
