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
@TableName("sys_role")
public class Role extends BaseModel {
    /** 角色id */
    @TableId(type = IdType.AUTO)
    private Long roleId;

    /** 角色名称 */
    private String roleName;

    /** 描述 */
    private String description;
}
