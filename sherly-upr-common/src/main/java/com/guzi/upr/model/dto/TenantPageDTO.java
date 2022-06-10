package com.guzi.upr.model.dto;

import com.guzi.upr.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/3/27
 */
@Data
public class TenantPageDTO extends PageQuery {

    /** 租户code */
    @ApiModelProperty("租户code")
    private String tenantCode;

    /** 租户名称 */
    @ApiModelProperty("租户名称")
    private String tenantName;
}
