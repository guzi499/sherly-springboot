package com.guzi.sherly.model.dto;

import com.guzi.sherly.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/11/15
 */
@Data
public class TenantPackagePageDTO extends PageQuery {

    /** 租户套餐名称 */
    @ApiModelProperty(value = "租户套餐名称")
    private String tenantPackageName;

}
