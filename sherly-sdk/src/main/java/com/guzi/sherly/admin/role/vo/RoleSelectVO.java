package com.guzi.sherly.admin.role.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/4/17
 */
@Data
public class RoleSelectVO {

    /** 角色编号 */
    @ApiModelProperty(value = "角色编号")
    private Long roleId;

    /** 角色名称 */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

}
