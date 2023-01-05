package com.guzi.sherly.admin.role.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/7/13
 */
@Data
public class RoleSelectDTO {
    /** 角色编号 */
    @ApiModelProperty("角色编号")
    private Long roleId;
}
