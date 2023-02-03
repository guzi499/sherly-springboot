package com.guzi.sherly.admin.menu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.admin.menu.enums.MenuTypeEnum;
import com.guzi.sherly.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 谷子毅
 * @date 2022/3/17
 */
@Data
@TableName("sys_menu")
@EqualsAndHashCode(callSuper = true)
public class MenuDO extends BaseModel {
    /** 菜单编号 */
    @TableId(type = IdType.AUTO)
    private Long menuId;

    /** 菜单名称 */
    private String menuName;

    /** 菜单类型{@link MenuTypeEnum} */
    private Integer menuType;

    /** 权限 */
    private String permission;

    /** 父菜单编号 */
    private Long parentId;

    /** 菜单路径 */
    private String link;

    /** 菜单图标 */
    private String icon;

    /** 排序 */
    private Integer sort;
}
