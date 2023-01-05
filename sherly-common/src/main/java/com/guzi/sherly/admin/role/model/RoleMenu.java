package com.guzi.sherly.admin.role.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 谷子毅
 * @date 2022/3/17
 */
@Data
@TableName("sys_role_menu")
@EqualsAndHashCode(callSuper = true)
public class RoleMenu extends BaseModel {
    /** 编号 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 角色编号 */
    private Long roleId;

    /** 菜单编号 */
    private Long menuId;
}
