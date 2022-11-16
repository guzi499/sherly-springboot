package com.guzi.sherly.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/4/5
 */
@Data
public class BasicMenuInfoVO {
    /** 菜单编号 */
    @ApiModelProperty(value = "菜单编号")
    private Long menuId;

    /** 菜单名称 */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /** 菜单路径 */
    @ApiModelProperty(value = "菜单路径")
    private String link;

    /** 菜单图标 */
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    /** 子菜单列表 */
    @ApiModelProperty(value = "子菜单列表")
    private List<BasicMenuInfoVO> children;
}
