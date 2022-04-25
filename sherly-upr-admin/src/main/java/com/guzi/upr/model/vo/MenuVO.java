package com.guzi.upr.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    /** 菜单id */
    @ApiModelProperty("菜单id")
    private Long menuId;

    /** 菜单名称 */
    @ApiModelProperty("菜单名称")
    private String menuName;

    /** 父菜单id */
    @ApiModelProperty("父菜单id")
    private Long parentId;

    /** 菜单路径 */
    @ApiModelProperty("菜单路径")
    private String link;

    /** 菜单图标 */
    @ApiModelProperty("菜单图标")
    private String icon;

    /** 排序 */
    @ApiModelProperty("排序")
    private Integer sort;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private List<MenuVO> children;
}
