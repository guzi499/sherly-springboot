package com.guzi.sherly.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author 谷子毅
 * @date 2022/6/25
 */
@Data
public class OssConfigVO {
    /** 配置id */
    @ApiModelProperty(value = "配置id")
    private Long configId;

    /** 配置名称 */
    @ApiModelProperty(value = "配置名称")
    private String configName;

    /** 存储方式[enum] */
    @ApiModelProperty(value = "存储方式[enum]")
    private Integer type;

    /** 描述 */
    @ApiModelProperty(value = "描述")
    private String description;

    /** 具体配置 */
    @ApiModelProperty(value = "具体配置")
    private Map<String, Object> config;
}
