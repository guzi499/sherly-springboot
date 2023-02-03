package com.guzi.sherly.admin.menu.vo;

import com.guzi.sherly.admin.menu.enums.MenuTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 付东辉
 * @date 2022/3/25
 */
@Data
public class MenuVO {
    /** 菜单编号 */
    @ApiModelProperty(value = "菜单编号")
    private Long menuId;

    /** 菜单类型{@link MenuTypeEnum} */
    @ApiModelProperty(value = "菜单类型[enum]")
    private MenuTypeEnum menuType;

    /** 权限 */
    @ApiModelProperty(value = "权限")
    private String permission;

    /** 菜单名称 */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /** 父菜单编号 */
    @ApiModelProperty(value = "父菜单编号")
    private Long parentId;

    /** 菜单路径 */
    @ApiModelProperty(value = "菜单路径")
    private String link;

    /** 菜单图标 */
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    /** 排序 */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private List<MenuVO> children;
}
