package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.upr.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/3/18
 */
@Data
@TableName("sys_permission")
public class Permission extends BaseModel {
    /** 权限id */
    @ApiModelProperty("权限id")
    @TableId(type = IdType.AUTO)
    private Long permissionId;

    /** 权限名 */
    @ApiModelProperty("权限名")
    private String permissionName;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** 父权限id */
    @ApiModelProperty("父权限id")
    private Long parentId = 0L;
}
