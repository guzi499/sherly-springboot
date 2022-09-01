package com.guzi.sherly.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 谷子毅
 * @date 2022/9/1
 */
@Data
public class FlowModelUpdateDTO {
    /** 模型id */
    @ApiModelProperty(value = "模型id")
    @NotBlank
    private String id;

    /** 模型json */
    @ApiModelProperty(value = "模型xml")
    @NotBlank
    private String modelXml;
}
