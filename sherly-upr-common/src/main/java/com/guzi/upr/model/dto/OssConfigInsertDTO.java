package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author 谷子毅
 * @date 2022/6/25
 */
@Data
public class OssConfigInsertDTO {
    /** 配置名称 */
    @ApiModelProperty(value = "配置名称", required = true)
    @NotBlank
    private String configName;

    /** 存储类型 */
    @ApiModelProperty(value = "存储类型", required = true, allowableValues = "OssTypeEnum.java")
    @NotNull
    private Integer type;

    /** 描述 */
    @ApiModelProperty(value = "描述")
    private String description;

    /** 具体配置 */
    @ApiModelProperty(value = "具体配置", required = true, allowableValues = "OssClientConfig.java接口的实现")
    @NotNull
    private Map<String, Object> config;
}
