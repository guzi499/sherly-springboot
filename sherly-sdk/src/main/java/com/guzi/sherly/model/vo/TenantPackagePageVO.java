package com.guzi.sherly.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/11/15
 */
@Data
public class TenantPackagePageVO {
    /** 租户套餐id */
    @ApiModelProperty("租户套餐id")
    private String tenantPackageId;

    /** 租户套餐名称 */
    @ApiModelProperty("租户套餐名称")
    private String tenantPackageName;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** 启用禁用[enum] */
    @ApiModelProperty("启用禁用[enum]")
    private Integer enable;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
