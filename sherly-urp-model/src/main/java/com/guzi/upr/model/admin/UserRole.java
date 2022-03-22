package com.guzi.upr.model.admin;

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
    /** 用户id */
    @ApiModelProperty("用户id")
    private Integer userId;

    /** 角色id */
    @ApiModelProperty("角色id")
    private Integer roleId;
}
