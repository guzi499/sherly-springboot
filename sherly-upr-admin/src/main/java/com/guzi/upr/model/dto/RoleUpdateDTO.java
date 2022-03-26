package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author fudon
 * @version 1.0
 * @date 2022/3/26 19:14
 */
@Data
public class RoleUpdateDTO {
    @ApiModelProperty("角色id")
    private Long roleId;
    /**
     * 角色名
     */
    @ApiModelProperty("角色名")
    private String roleName;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("权限ids")
    private List<Long> permissions;

}
