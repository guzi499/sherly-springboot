package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author 谷子毅
 * @date 2022/4/6
 */
@Data
public class DepartmentUpdateDTO {
    /** 部门id */
    @ApiModelProperty("部门id")
    @NotNull
    private Long departmentId;

    /** 部门名称 */
    @ApiModelProperty("部门名称")
    private String departmentName;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** 父部门id */
    @ApiModelProperty("父部门id")
    private Long parentId;

    /** 排序 */
    @ApiModelProperty("排序")
    @Range(min = 1, max = 999)
    private Integer sort;
}
