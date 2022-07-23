package com.guzi.upr.model.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/6/29
 */
@Data
public class OssFilePageVO {
    /** 文件id */
    @ApiModelProperty(value = "文件id")
    private Long fileId;

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
