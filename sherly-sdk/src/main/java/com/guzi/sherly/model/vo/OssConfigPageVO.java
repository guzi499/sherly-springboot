package com.guzi.sherly.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/6/24
 */
@Data
public class OssConfigPageVO {
    /** 配置编号 */
    @ApiModelProperty(value = "配置编号")
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

    /** 启用禁用[enum] */
    @ApiModelProperty(value = "启用禁用[enum]")
    private Integer enable;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
