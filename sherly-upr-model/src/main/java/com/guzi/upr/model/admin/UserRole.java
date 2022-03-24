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
@TableName("sys_user_role")
public class UserRole extends BaseModel {
    /** id */
    @ApiModelProperty("id")
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户id */
    @ApiModelProperty("用户id")
    private Long userId;

    /** 角色id */
    @ApiModelProperty("角色id")
    private Long roleId;
}
