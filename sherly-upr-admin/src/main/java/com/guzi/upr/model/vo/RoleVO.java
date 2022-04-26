package com.guzi.upr.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/4/7
 */
@Data
public class RoleVO {
    /** 角色id */
    @ApiModelProperty("角色id")
    private Long roleId;

    /** 角色名称 */
    @ApiModelProperty("角色名称")
    private String roleName;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** 菜单ids */
    @ApiModelProperty("菜单ids")
    private List<Long> menuIds;

    /** 权限ids */
    @ApiModelProperty("权限ids")
    private List<Long> permissionIds;
}
