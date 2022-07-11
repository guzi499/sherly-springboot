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
public class MenuSelectVO implements TreeAble {

    /** 菜单id */
    @ApiModelProperty(value = "菜单id")
    private Long menuId;

    /** 菜单名称 */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /** 父菜单id */
    @ApiModelProperty(value = "父菜单id")
    @JsonIgnore
    private Long parentId;

    /** 排序 */
    @ApiModelProperty(value = "排序")
    @JsonIgnore
    private Integer sort;

    private List<? extends TreeAble> children;

    @Override
    @JsonIgnore
    public Long getId() {
        return this.menuId;
    }

    @Override
    public void setChildren(List<? extends TreeAble> list) {
        this.children = list;
    }
}
