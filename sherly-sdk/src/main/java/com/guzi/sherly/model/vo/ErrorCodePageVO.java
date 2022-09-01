package com.guzi.sherly.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 李仁杰
 * @date 2022/9/1
 */
@Data
public class ErrorCodePageVO {

    /** 错误id */
    @ApiModelProperty(value = "错误id")
    private Integer errorId;

    /** 错误码 */
    @ApiModelProperty(value = "错误代码")
    private String errorCode;

    /** 错误信息 */
    @ApiModelProperty(value = "错误信息")
    private String message;

    /** 错误描述 */
    @ApiModelProperty(value = "错误描述")
    private String description;

    /** 模块代码 */
    @ApiModelProperty(value = "模块代码")
    private String moduleCode;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
