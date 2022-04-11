package com.guzi.upr.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 付东辉
 * @date 2022/3/25
 */

@Data
public class MenuParentDTO {
    /**
     * 菜单id
     */
    @ApiModelProperty("菜单id")
    @TableId(type = IdType.AUTO)
    private Long menuId;

    /**
     * 菜单名
     */
    @ApiModelProperty("菜单名")
    private String menuName;

    /**
     * 父菜单id
     */
    @ApiModelProperty("父菜单id")
    private Long parentId;

    /**
     * 菜单路径
     */
    @ApiModelProperty("菜单路径")
    private String link;

    /**
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
    private String icon;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    @TableField(exist = false)
    private List<MenuParentDTO> children;
}
