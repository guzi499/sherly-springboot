package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Data
public class EmailConfigDTO {
    /** id */
    @ApiModelProperty("id")
    @NotNull
    private Long id;

    /** 邮件服务器SMTP地址 */
    @ApiModelProperty("邮件服务器SMTP地址")
    @NotBlank
    private String host;

    /** 邮件服务器SMTP端口 */
    @ApiModelProperty("邮件服务器SMTP端口")
    @NotBlank
    private String port;

    /** 发件人名称 */
    @ApiModelProperty("发件人名称")
    @NotBlank
    private String senderUser;

    /** 密码 */
    @ApiModelProperty("密码")
    @NotBlank
    private String password;

    /** 发件人邮箱 */
    @ApiModelProperty("发件人邮箱")
    @Email
    private String senderEmail;
}
