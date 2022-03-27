package com.guzi.upr.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/3/27
 */
@Data
public class PageQuery {
    /** 当前页 */
    @ApiModelProperty("当前页")
    private Long current = 0L;

    /** 页大小 */
    @ApiModelProperty("页大小")
    private Long size = 10L;

    /**
     * 获取mybatis-plus分页对象
     * @return
     */
    public IPage getPage() {
        return new Page(current, size);
    }
}
