package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 周孟凡
 * @date 2022/3/30
 */
@Data
public class DepartmentInsertDTO {
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
