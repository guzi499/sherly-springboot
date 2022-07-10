package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/5/25
 */
@Data
public class OnlineUserQueryDTO {

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String phone;
}
