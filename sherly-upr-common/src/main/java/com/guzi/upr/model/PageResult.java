package com.guzi.upr.model;

import lombok.Data;

import java.util.List;

/**
 * 通用结果分页返回类
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/22
 */
@Data
public class PageResult<T> {

    private List<T> result;

    private Long current;

    private Long size;

    private Long total;

    private PageResult(List<T> result, Long current, Long size, Long total) {
        this.result = result;
        this.current = current;
        this.size = size;
        this.total = total;
    }

    public static <T> PageResult build(List<T> result, Long current, Long size, Long total) {
        return new PageResult(result, current, size, total);
    }
}
