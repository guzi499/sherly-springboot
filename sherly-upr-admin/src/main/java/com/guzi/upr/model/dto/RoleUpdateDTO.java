package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author fudon
 * @version 1.0
 * @date 2022/3/26 19:14
 */
@Data
public class RoleUpdateDTO {
    /** 角色id */
    @ApiModelProperty("角色id")
    private Long roleId;

    /** 角色名 */
    @ApiModelProperty("角色名")
    private String roleName;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** 菜单ids */
    @ApiModelProperty("菜单ids")
    private List<Long> menuIds;

    /** 权限ids */
    @ApiModelProperty("权限ids")
    private List<Long> permissionsIds;

}
