package com.guzi.upr.model.dto;

import com.guzi.upr.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/7/15
 */
@Data
public class OperationLogPageDTO extends PageQuery {
    /** 日志类型[enum] */
    @ApiModelProperty("日志类型[enum]")
    private String type;
}
