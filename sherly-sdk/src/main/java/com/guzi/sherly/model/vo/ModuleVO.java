package com.guzi.sherly.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 李仁杰
 * @date 2022/9/1
 */
@Data
public class ModuleVO {

    /** 模块编号 */
    @ApiModelProperty(value = "模块编号")
    private Integer moduleId;

    /** 模块代码 */
    @ApiModelProperty(value = "模块代码")
    private String moduleCode;

    /** 模块名称 */
    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    /** 排序 */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /** 父模块编号 */
    @ApiModelProperty(value = "父模块编号")
    private Integer parentId;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private List<ModuleVO> children;
}
