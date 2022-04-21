package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Data
public class PermissionInsertDTO {

    /** 权限名称 */
    @ApiModelProperty("权限名称")
    @NotBlank
    private String permissionName;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** 父权限id */
    @ApiModelProperty("父权限id")
    @NotNull
    private Long parentId;

}
