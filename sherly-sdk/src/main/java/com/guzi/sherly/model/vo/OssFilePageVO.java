package com.guzi.sherly.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/6/29
 */
@Data
public class OssFilePageVO {
    /** 文件编号 */
    @ApiModelProperty(value = "文件编号")
    private Long fileId;

    /** 文件名称 */
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    /** 文件相对路径 */
    @ApiModelProperty(value = "文件相对路径")
    private String path;

    /** 文件类型 */
    @ApiModelProperty(value = "文件类型")
    private String fileType;

    /** 文件大小 */
    @ApiModelProperty(value = "文件大小")
    private Integer size;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
