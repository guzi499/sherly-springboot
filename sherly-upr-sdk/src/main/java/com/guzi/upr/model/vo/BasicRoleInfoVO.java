package com.guzi.upr.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/4/6
 */
@Data
public class BasicRoleInfoVO {
    /** 角色id */
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /** 角色名称 */
    @ApiModelProperty(value = "角色名称")
    private String roleName;
}
