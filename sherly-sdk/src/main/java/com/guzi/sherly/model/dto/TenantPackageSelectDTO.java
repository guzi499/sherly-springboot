package com.guzi.sherly.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/11/16
 */
@Data
public class TenantPackageSelectDTO {
    /** 启用禁用[enum] */
    @ApiModelProperty(value = "启用禁用[enum]")
    private Integer enable;
}
