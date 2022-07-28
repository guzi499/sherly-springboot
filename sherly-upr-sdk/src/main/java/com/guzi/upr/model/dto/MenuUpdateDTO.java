package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Data
public class MenuUpdateDTO {

    /** 菜单id */
    @ApiModelProperty(value = "菜单id", required = true)
    @NotNull
    private Long menuId;

    /** 菜单名称 */
    @ApiModelProperty(value = "菜单名称", required = true)
    @NotBlank
    private String menuName;

    /** 菜单类型[enum] */
    @ApiModelProperty(value = "菜单类型[enum]", required = true)
    @NotNull
    private Integer menuType;

    /** 权限 */
    @ApiModelProperty(value = "权限")
    private String permission;

    /** 父菜单id */
    @ApiModelProperty(value = "父菜单id", required = true)
    @NotNull
    private Long parentId;

    /** 菜单路径  */
    @ApiModelProperty(value = "菜单路径")
    private String link;

    /** 菜单图标 */
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    /** 排序 */
    @ApiModelProperty(value = "排序", required = true)
    @NotNull
    private Integer sort;
}
