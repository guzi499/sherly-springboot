package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.upr.constants.CommonConstants;
import com.guzi.upr.model.BaseModel;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/3/17
 */
@Data
@TableName("sys_menu")
public class Menu extends BaseModel {
    /** 菜单id */
    @TableId(type = IdType.AUTO)
    private Long menuId;

    /** 菜单名称 */
    private String menuName;

    /** 菜单类型 1目录 2菜单 3按钮 {@link CommonConstants} */
    private Integer menuType;

    /** 权限 */
    private String permission;

    /** 父菜单id */
    private Long parentId;

    /** 菜单路径 */
    private String link;

    /** 菜单图标 */
    private String icon;

    /** 排序 */
    private Integer sort;
}
