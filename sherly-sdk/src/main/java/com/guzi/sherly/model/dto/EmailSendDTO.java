package com.guzi.sherly.model.dto;

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
    @ApiModelProperty(value = "收件人邮箱", required = true)
    @NotEmpty
    private List<String> tos;

    /** 主题 */
    @ApiModelProperty(value = "主题", required = true)
    @NotBlank
    private String subject;

    /** 正文 */
    @ApiModelProperty(value = "正文", required = true)
    @NotBlank
    private String content;
}
