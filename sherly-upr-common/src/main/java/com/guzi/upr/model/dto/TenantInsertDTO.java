package com.guzi.upr.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/3/27
 */
@Data
public class TenantInsertDTO {
    /** 租户code */
    @ApiModelProperty("租户code")
    @NotBlank
    private String tenantCode;

    /** 租户名称 */
    @ApiModelProperty("租户名称")
    @NotBlank
    private String tenantName;

    /** 联系人 */
    @ApiModelProperty("联系人")
    private String contactUser;

    /** 联系电话 */
    @ApiModelProperty("联系电话")
    private String contactPhone;

    /** 过期时间 */
    @ApiModelProperty("过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull
    private Date expireTime;

    /** 用户上限 */
    @ApiModelProperty("用户上限")
    @Min(100)
    @NotBlank
    private Long userLimit;
}
