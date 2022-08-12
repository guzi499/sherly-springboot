package com.guzi.sherly.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/7/13
 */
@Data
public class RoleSelectDTO {
    /** 角色id */
    @ApiModelProperty("角色id")
    private Long roleId;
}
