package com.guzi.sherly.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ModuleUpdateDTO {

    @ApiModelProperty(value = "模块id")
    private Integer moduleId;

    @ApiModelProperty(value = "模块名称", required = true)
    @NotBlank
    private String moduleName;

    @ApiModelProperty(value = "模块代码", required = true)
    @NotBlank
    private String moduleCode;

    @ApiModelProperty(value = "父模块id", required = true)
    @NotNull
    private Integer parentId;

    @ApiModelProperty(value = "排序", required = true)
    @NotNull
    private Integer sort;
}
