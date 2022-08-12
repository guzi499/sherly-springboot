package com.guzi.sherly.model.vo;

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
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /** 角色名称 */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /** 描述 */
    @ApiModelProperty(value = "描述")
    private String description;

    /** 菜单ids */
    @ApiModelProperty(value = "菜单ids")
    private List<Long> menuIds;
}
