package com.guzi.upr.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页请求对象，所有分页请求DTO都需继承此类
 * @author 谷子毅
 * @date 2022/3/27
 */
@Data
public class PageQuery {
    /** 当前页 */
    @ApiModelProperty(value = "当前页")
    private Long current = 0L;

    /** 页大小 */
    @ApiModelProperty(value = "页大小")
    private Long size = 10L;
}
