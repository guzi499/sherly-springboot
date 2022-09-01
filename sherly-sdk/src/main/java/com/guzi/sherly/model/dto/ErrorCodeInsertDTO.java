package com.guzi.sherly.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ErrorCodeInsertDTO {

    /** 错误代码 */
    @ApiModelProperty(value = "错误代码", required = true)
    @NotBlank
    private String errorCode;

    /** 错误信息 */
    @ApiModelProperty(value = "错误信息", required = true)
    @NotBlank
    private String message;

    /** 错误描述 */
    @ApiModelProperty(value = "错误描述")
    private String description;

    /** 模块代码 */
    @ApiModelProperty(value = "模块代码", required = true)
    @NotBlank
    private String moduleCode;
}
