package com.guzi.upr.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    /** 部门id */
    @ApiModelProperty(value = "部门id")
    private Long departmentId;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    /** 描述 */
    @ApiModelProperty(value = "描述")
    private String description;

    /** 父部门id */
    @ApiModelProperty(value = "父部门id")
    private Long parentId;

    /** 排序 */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private List<DepartmentVO> children;
}
