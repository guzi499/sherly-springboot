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

    /** 模型元数据 */
    @ApiModelProperty(value = "模型元数据")
    private String metaInfo;

    /** 模型json */
    @ApiModelProperty(value = "模型xml")
    @NotBlank
    private String modelXml;
}
