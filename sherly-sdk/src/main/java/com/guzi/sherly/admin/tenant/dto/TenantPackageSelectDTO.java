package com.guzi.sherly.admin.tenant.dto;

import com.guzi.sherly.common.enums.UsableEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/11/16
 */
@Data
public class TenantPackageSelectDTO {
    /** 可用性 */
    @ApiModelProperty(value = "可用性")
    private UsableEnum enable;
}
