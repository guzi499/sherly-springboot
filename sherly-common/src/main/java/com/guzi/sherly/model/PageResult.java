package com.guzi.sherly.model;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 通用分页返回类
 * @author 谷子毅
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

    public static <T> PageResult buildEmpty() {
        return new PageResult(Collections.emptyList(), 1L, 10L, 0L);
    }
}
