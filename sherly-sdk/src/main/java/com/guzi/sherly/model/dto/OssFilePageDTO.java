package com.guzi.sherly.model.dto;

import cn.hutool.core.date.DatePattern;
import com.guzi.sherly.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/6/29
 */
@Data
public class OssFilePageDTO extends PageQuery {

    /** 配置id */
    @ApiModelProperty(value = "配置id")
    private Long configId;

    /** 文件名称 */
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    /** 文件相对路径 */
    @ApiModelProperty(value = "文件相对路径")
    private String path;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date beginTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date endTime;
}
