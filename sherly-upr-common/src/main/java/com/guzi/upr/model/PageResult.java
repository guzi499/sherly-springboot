package com.guzi.upr.model;

/**
 * 通用结果分页返回类
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/22
 */
public class PageResult<T> extends Result<T> {

    private Integer current;

    private Integer size;

    private Integer total;

    public PageResult(T data, Integer current, Integer size, Integer total) {
        super(data);
        this.current = current;
        this.size = size;
        this.total = total;
    }
}
