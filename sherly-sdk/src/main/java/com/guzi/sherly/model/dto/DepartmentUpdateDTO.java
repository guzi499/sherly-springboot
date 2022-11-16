package com.guzi.sherly.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 谷子毅
 * @date 2022/4/6
 */
@Data
public class DepartmentUpdateDTO {
    /** 部门编号 */
    @ApiModelProperty(value = "部门编号", required = true)
    @NotNull
    private Long departmentId;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", required = true)
    @NotBlank
    private String departmentName;

    /** 描述 */
    @ApiModelProperty(value = "描述")
    private String description;

    /** 父部门编号 */
    @ApiModelProperty(value = "父部门编号", required = true)
    @NotNull
    private Long parentId;

    /** 排序 */
    @ApiModelProperty(value = "排序", required = true)
    @NotNull
    private Integer sort;
}
