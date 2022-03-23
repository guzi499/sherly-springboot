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
 * @date 2022/3/17
 */
@Data
@TableName("sys_role")
public class Role extends BaseModel {
    /** 角色id */
    @ApiModelProperty("角色id")
    @TableId(type = IdType.AUTO)
    private Integer roleId;

    /** 角色名 */
    @ApiModelProperty("角色名")
    private String roleName;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;
}
