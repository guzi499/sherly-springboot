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
    /** 收件人邮箱 */
    @ApiModelProperty("收件人邮箱")
    private List<String> tos;

    /** 主题 */
    @ApiModelProperty("主题")
    private String subject;

    /** 正文 */
    @ApiModelProperty("正文")
    private String content;
}
