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
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseModel {
    /** 角色编号 */
    @TableId(type = IdType.AUTO)
    private Long roleId;

    /** 角色名称 */
    private String roleName;

    /** 描述 */
    private String description;
}
