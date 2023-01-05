package com.guzi.sherly.admin.module.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 李仁杰
 * @date 2022/9/1
 */
@Data
public class ModuleUpdateDTO {

    @ApiModelProperty(value = "模块编号", required = true)
    @NotNull
    private Integer moduleId;

    @ApiModelProperty(value = "模块名称", required = true)
    @NotBlank
    private String moduleName;

    @ApiModelProperty(value = "模块代码", required = true)
    @NotBlank
    private String moduleCode;

    @ApiModelProperty(value = "父模块编号", required = true)
    @NotNull
    private Integer parentId;

    @ApiModelProperty(value = "排序", required = true)
    @NotNull
    private Integer sort;
}
