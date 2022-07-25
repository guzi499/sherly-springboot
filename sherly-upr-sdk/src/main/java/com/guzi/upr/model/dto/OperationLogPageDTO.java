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
    @ApiModelProperty(value = "日志类型[enum]", example = "CommonConstants.java")
    private String type;

    /** 用户id */
    @ApiModelProperty(value = "用户id")
    private Long userId;
}
