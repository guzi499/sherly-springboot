package com.guzi.sherly.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ModuleVO {

    /** 模块id */
    @ApiModelProperty(value = "模块id")
    private Integer moduleId;

    /** 模块名称 */
    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    /** 模块代码 */
    @ApiModelProperty(value = "模块代码")
    private String moduleCode;

    /** 父模块id */
    @ApiModelProperty(value = "父模块id")
    private Integer parentId;

    /** 排序 */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private List<ModuleVO> children;
}
