package com.guzi.sherly.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 周孟凡
 * @date 2022/3/30
 */
@Data
public class DepartmentInsertDTO {
    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", required = true)
    @NotBlank
    private String departmentName;

    /** 描述 */
    @ApiModelProperty(value = "描述")
    private String description;

    /** 父部门id */
    @ApiModelProperty(value = "父部门id", required = true)
    @NotNull
    private Long parentId;

    /** 排序 */
    @ApiModelProperty(value = "排序", required = true)
    @NotNull
    private Integer sort;
}
