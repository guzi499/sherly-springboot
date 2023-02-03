package com.guzi.sherly.admin.tenant.vo;

import com.guzi.sherly.admin.user.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/11/15
 */
@Data
public class TenantPackagePageVO {
    /** 租户套餐编号 */
    @ApiModelProperty("租户套餐编号")
    private String tenantPackageId;

    /** 租户套餐名称 */
    @ApiModelProperty("租户套餐名称")
    private String tenantPackageName;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** 可用性 */
    @ApiModelProperty("可用性")
    private GenderEnum enable;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
