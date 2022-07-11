package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.upr.model.BaseModel;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/3/17
 */
@Data
@TableName("sys_role_menu")
public class RoleMenu extends BaseModel {
    /** id */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 角色id */
    private Long roleId;

    /** 菜单id */
    private Long menuId;
}
