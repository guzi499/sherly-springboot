package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 付东辉
 * @date 2022/3/26
 */
@Data
public class RoleInsertDTO {
    /** 角色名称 */
    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank
    private String roleName;

    /** 描述 */
    @ApiModelProperty(value = "描述")
    private String description;
}
