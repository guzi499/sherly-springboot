package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/4/6
 */
@Data
public class DepartmentUpdateDTO {
    /** 部门id */
    @ApiModelProperty("部门id")
    private Long deptId;

    /** 部门名 */
    @ApiModelProperty("部门名")
    private String deptName;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** 父部门id */
    @ApiModelProperty("父部门id")
    private Long parentId;

}
