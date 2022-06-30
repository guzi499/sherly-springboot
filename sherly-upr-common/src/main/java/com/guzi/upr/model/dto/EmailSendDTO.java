package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Data
public class EmailSendDTO {
    /** 收件人邮箱 */
    @ApiModelProperty("收件人邮箱")
    @NotEmpty
    private List<String> tos;

    /** 主题 */
    @ApiModelProperty("主题")
    @NotBlank
    private String subject;

    /** 正文 */
    @ApiModelProperty("正文")
    @NotBlank
    private String content;
}
