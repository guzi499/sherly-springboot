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
    /** 操作日志类型 */
    @ApiModelProperty("操作日志类型")
    private String type;
}
