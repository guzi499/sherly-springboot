package com.guzi.sherly.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/4/6
 */
@Data
public class DepartmentVO {
    /** 部门编号 */
    @ApiModelProperty(value = "部门编号")
    private Long departmentId;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    /** 描述 */
    @ApiModelProperty(value = "描述")
    private String description;

    /** 父部门编号 */
    @ApiModelProperty(value = "父部门编号")
    private Long parentId;

    /** 排序 */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private List<DepartmentVO> children;
}
