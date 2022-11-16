package com.guzi.sherly.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 付东辉
 * @date 2022/3/26
 */
@Data
public class RoleUpdateDTO {
    /** 角色编号 */
    @ApiModelProperty(value = "角色编号", required = true)
    @NotNull
    private Long roleId;

    /** 角色名称 */
    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank
    private String roleName;

    /** 描述 */
    @ApiModelProperty(value = "描述")
    private String description;

    /** 菜单ids */
    @ApiModelProperty(value = "菜单ids")
    private List<Long> menuIds;

}
