package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Data
public class EmailSendDTO {
    @ApiModelProperty("收件人")
    private List<String> tos;

    @ApiModelProperty("主题")
    private String subject;

    @ApiModelProperty("正文")
    private String content;
}
