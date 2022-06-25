package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author 谷子毅
 * @date 2022/6/26
 */
@Data
public class OssConfigUpdateDTO {

    /** 配置id */
    @ApiModelProperty("配置id")
    private Long configId;

    /** 配置名称 */
    @ApiModelProperty("配置名称")
    @NotBlank
    private String configName;

    /** 存储类型 */
    @ApiModelProperty("存储类型")
    @NotNull
    private Integer type;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** 具体配置 */
    @ApiModelProperty("具体配置")
    private Map<String, Object> config;
}
