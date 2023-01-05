package com.guzi.sherly.admin.login.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/7/30
 */
@Data
public class LoginTenantVO {
    /** 租户代码 */
    @ApiModelProperty(value = "租户代码")
    private String tenantCode;

    /** 租户名称 */
    @ApiModelProperty(value = "租户名称")
    private String tenantName;
}
