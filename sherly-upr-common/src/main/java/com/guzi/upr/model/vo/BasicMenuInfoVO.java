package com.guzi.upr.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/4/5
 */
@Data
public class BasicMenuInfoVO {
    /** 菜单id */
    @ApiModelProperty("菜单id")
    private Long menuId;

    /** 菜单名称 */
    @ApiModelProperty("菜单名称")
    private String menuName;

    /** 菜单路径 */
    @ApiModelProperty("菜单路径")
    private String link;

    /** 菜单图标 */
    @ApiModelProperty("菜单图标")
    private String icon;

    /** 子菜单列表 */
    @ApiModelProperty("子菜单列表")
    private List<BasicMenuInfoVO> children;
}
