package com.guzi.upr.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guzi.upr.model.TreeAble;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/4/17
 */
@Data
public class DepartmentSelectVO implements TreeAble {

    /** 部门id */
    @ApiModelProperty("部门id")
    private Long departmentId;

    /** 部门名称 */
    @ApiModelProperty("部门名称")
    private String departmentName;

    /** 父部门id */
    @ApiModelProperty("父部门id")
    private Long parentId;

    private List<? extends TreeAble> children;

    @Override
    @JsonIgnore
    public Long getId() {
        return this.departmentId;
    }

    @Override
    @JsonIgnore
    public Integer getSort() {
        return 0;
    }

    @Override
    public void setChildren(List<? extends TreeAble> list) {
        this.children = list;
    }
}
