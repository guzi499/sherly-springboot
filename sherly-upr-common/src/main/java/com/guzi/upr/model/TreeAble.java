package com.guzi.upr.model;

import java.util.List;

/**
 * 树状结构base接口，所有树形对象都需基础此接口
 *
 * @author 谷子毅
 * @date 2022/4/17
 */
public interface TreeAble {

    Long getId();

    Long getParentId();

    Integer getSort();

    void setChildren(List<? extends TreeAble> list);

}
