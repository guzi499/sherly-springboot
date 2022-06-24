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
    @ApiModelProperty("配置id")
    private Long configId;

    /** 配置名称 */
    @ApiModelProperty("配置名称")
    private String configName;

    /** 存储类型 */
    @ApiModelProperty("存储类型")
    private Integer type;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** 0不可用 1可用 */
    @ApiModelProperty("0不可用 1可用")
    private Integer enable;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
