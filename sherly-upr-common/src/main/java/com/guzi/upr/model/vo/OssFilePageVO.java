package com.guzi.upr.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/6/29
 */
@Data
public class OssFilePageVO {
    /** 文件id */
    @ApiModelProperty("文件id")
    private Long fileId;

    /** 文件相对路径 */
    @ApiModelProperty("文件相对路径")
    private String path;

    /** 访问url */
    @ApiModelProperty("访问url")
    private String url;

    /** 文件类型 */
    @ApiModelProperty("文件类型")
    private String fileType;

    /** 文件大小 */
    @ApiModelProperty("文件大小")
    private Integer size;
}
