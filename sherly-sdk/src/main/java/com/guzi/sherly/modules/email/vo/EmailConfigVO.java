package com.guzi.sherly.modules.email.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Data
public class EmailConfigVO {
    /** 编号 */
    @ApiModelProperty(value = "编号")
    private Integer id;

    /** 邮件服务器SMTP地址 */
    @ApiModelProperty(value = "邮件服务器SMTP地址")
    private String host;

    /** 邮件服务器SMTP端口 */
    @ApiModelProperty(value = "邮件服务器SMTP端口")
    private String port;

    /** 发件人名称 */
    @ApiModelProperty(value = "发件人名称")
    private String senderUser;

    /** 密码 */
    @ApiModelProperty(value = "密码")
    private String password;

    /** 发件人邮箱 */
    @ApiModelProperty(value = "发件人邮箱")
    private String senderEmail;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
