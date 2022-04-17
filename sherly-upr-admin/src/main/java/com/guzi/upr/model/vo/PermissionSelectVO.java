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
public class PermissionSelectVO implements TreeAble {
    /** 权限id */
    @ApiModelProperty("权限id")
    private Long permissionId;

    /** 权限名称 */
    @ApiModelProperty("权限名称")
    private String permissionName;

    /** 父权限id */
    @ApiModelProperty("父权限id")
    private Long parentId;

    private List<? extends TreeAble> children;

    @Override
    @JsonIgnore
    public Long getId() {
        return this.permissionId;
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
