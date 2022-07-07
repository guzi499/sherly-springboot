package com.guzi.upr.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.guzi.upr.model.admin.OssClientConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/6/25
 */
@Data
public class OssConfigVO {
    /** 配置id */
    @ApiModelProperty(value = "配置id")
    @TableId(type = IdType.AUTO)
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

    /** 具体配置 */
    @ApiModelProperty(value = "具体配置")
    private OssClientConfig config;
}
