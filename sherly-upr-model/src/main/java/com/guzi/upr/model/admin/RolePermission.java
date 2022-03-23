package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.upr.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/18
 */
@Data
@TableName("sys_role_permission")
public class RolePermission extends BaseModel {
    /** id */
    @ApiModelProperty("id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 角色id */
    @ApiModelProperty("角色id")
    private Integer roleId;

    /** 权限id */
    @ApiModelProperty("权限id")
    private Integer permissionId;
}
