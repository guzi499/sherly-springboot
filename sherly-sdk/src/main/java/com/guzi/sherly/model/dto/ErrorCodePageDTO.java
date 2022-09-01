package com.guzi.sherly.model.dto;

import com.guzi.sherly.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ErrorCodePageDTO extends PageQuery {

    /** 错误代码 */
    @ApiModelProperty(value = "错误代码")
    private String errorCode;

    /** 错误信息 */
    @ApiModelProperty(value = "错误信息")
    private String message;

    /** 模块代码 */
    @ApiModelProperty(value = "模块代码")
    private String moduleCode;
}
