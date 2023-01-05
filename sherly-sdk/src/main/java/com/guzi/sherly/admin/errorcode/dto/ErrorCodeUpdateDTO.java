package com.guzi.sherly.admin.errorcode.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 李仁杰
 * @date 2022/9/1
 */
@Data
public class ErrorCodeUpdateDTO {

    /** 错误编号 */
    @ApiModelProperty(value = "错误编号", required = true)
    @NotNull
    private Integer errorId;

    /** 错误信息 */
    @ApiModelProperty(value = "错误信息", required = true)
    @NotBlank
    private String message;

    /** 错误描述 */
    @ApiModelProperty(value = "错误描述")
    private String description;
}
