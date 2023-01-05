package com.guzi.sherly.common.model;

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

    private Long total;

    private PageResult(List<T> result, Long total) {
        this.result = result;
        this.total = total;
    }

    public static <T> PageResult<T> build(List<T> result, Long total) {
        return new PageResult<>(result, total);
    }

    public static <T> PageResult<T> buildEmpty() {
        return new PageResult<>(Collections.emptyList(), 0L);
    }
}
