package com.guzi.upr.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Data
public class EmailConfigDTO {
    /** id */
    @ApiModelProperty("id")
    private Long id;

    /** 邮件服务器SMTP地址 */
    @ApiModelProperty("邮件服务器SMTP地址")
    private String host;

    /** 邮件服务器SMTP端口 */
    @ApiModelProperty("邮件服务器SMTP端口")
    private String port;

    /** 发件者用户名 */
    @ApiModelProperty("发件者用户名")
    private String user;

    /** 密码 */
    @ApiModelProperty("密码")
    private String pass;

    /** 发件人邮箱 */
    @ApiModelProperty("发件人邮箱")
    private String fromUser;
}
