package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 周孟凡
 * @date 2022/3/30
 */
@Data
public class DepartmentInsertDTO {
    /** 部门名称 */
    @ApiModelProperty("部门名称")
    @NotBlank
    private String departmentName;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** 父部门id */
    @ApiModelProperty("父部门id")
    @NotNull
    private Long parentId;

    /** 排序 */
    @ApiModelProperty("排序")
    @Range(min = 1, max = 999)
    private Integer sort;
}
