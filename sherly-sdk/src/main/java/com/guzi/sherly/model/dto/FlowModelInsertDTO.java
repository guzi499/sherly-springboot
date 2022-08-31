package com.guzi.sherly.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 谷子毅
 * @date 2022/8/31
 */
@Data
public class FlowModelInsertDTO {
    /** 模型名称 */
    @ApiModelProperty(value = "模型名称")
    @NotBlank
    private String name;

    /** 模型key */
    @ApiModelProperty(value = "模型key")
    @NotBlank
    private String key;

    /** 模型描述 */
    @ApiModelProperty(value = "模型描述")
    private String description;

    /** 模型json */
    @ApiModelProperty(value = "模型xml")
    private String modelXml;
}
