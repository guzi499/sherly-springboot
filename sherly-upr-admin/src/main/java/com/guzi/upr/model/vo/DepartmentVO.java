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
    private Long parentId = 0L;

    /** 排序 */
    @ApiModelProperty("排序")
    private Integer sort;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private List<DepartmentVO> children;
}
