package com.guzi.sherly.modules.email.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Data
public class EmailConfigDTO {
    /** 编号 */
    @ApiModelProperty(value = "编号")
    private Integer id;

    /** 邮件服务器SMTP地址 */
    @ApiModelProperty(value = "邮件服务器SMTP地址", required = true)
    @NotBlank
    private String host;

    /** 邮件服务器SMTP端口 */
    @ApiModelProperty(value = "邮件服务器SMTP端口", required = true)
    @NotBlank
    private String port;

    /** 发件人名称 */
    @ApiModelProperty(value = "发件人名称", required = true)
    @NotBlank
    private String senderUser;

    /** 密码 */
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank
    private String password;

    /** 发件人邮箱 */
    @ApiModelProperty(value = "发件人邮箱", required = true)
    @Email
    private String senderEmail;
}
