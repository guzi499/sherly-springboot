package com.guzi.upr.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/6/24
 */
@Data
public class OssConfigPageVO {
    /** 配置id */
    @ApiModelProperty(value = "配置id")
    private Long configId;

    /** 配置名称 */
    @ApiModelProperty(value = "配置名称")
    private String configName;

    /** 存储类型 */
    @ApiModelProperty(value = "存储类型", allowableValues = "OssTypeEnum.java")
    private Integer type;

    /** 描述 */
    @ApiModelProperty(value = "描述")
    private String description;

    /** 启用禁用 */
    @ApiModelProperty(value = "启用禁用", allowableValues = "CommonConstants.java")
    private Integer enable;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
