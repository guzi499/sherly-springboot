package com.guzi.sherly.admin.errorcode.dto;

import com.guzi.sherly.common.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 李仁杰
 * @date 2022/9/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ErrorCodePageDTO extends PageQuery {

    /** 错误代码 */
    @ApiModelProperty(value = "错误代码")
    private String errorCode;

    /** 错误信息 */
    @ApiModelProperty(value = "错误信息")
    private String message;

    /** 模块编号 */
    @ApiModelProperty(value = "模块编号")
    private Integer moduleId;
}
