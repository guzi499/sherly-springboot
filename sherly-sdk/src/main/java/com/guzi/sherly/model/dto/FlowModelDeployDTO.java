package com.guzi.sherly.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/9/2
 */
@Data
public class FlowModelDeployDTO {
    /** 模型id */
    @ApiModelProperty(value = "模型id", required = true)
    private String id;
}
