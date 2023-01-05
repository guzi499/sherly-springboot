package com.guzi.sherly.admin.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/5/25
 */
@Data
public class UserOnlineSelectDTO {

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String phone;
}
