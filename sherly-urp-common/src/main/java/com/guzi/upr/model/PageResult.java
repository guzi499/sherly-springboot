package com.guzi.upr.model;

import lombok.Data;

/**
 * 通用结果分页返回类
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/22
 */
@Data
public class PageResult extends Result {

    private Integer current;

    private Integer size;

    private Integer total;

    public PageResult(Integer current, Integer size, Integer total) {
        this.current = current;
        this.size = size;
        this.total = total;
    }
}
