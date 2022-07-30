package com.guzi.upr.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/7/30
 */
@Data
public class LoginTenantVO {
    /** 租户code */
    @ApiModelProperty(value = "租户code")
    private String tenantCode;

    /** 租户名称 */
    @ApiModelProperty(value = "租户名称")
    private String tenantName;
}
