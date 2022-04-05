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
    @ApiModelProperty("角色id")
    private Long roleId;

    /** 角色名 */
    @ApiModelProperty("角色名")
    private String roleName;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;
}
