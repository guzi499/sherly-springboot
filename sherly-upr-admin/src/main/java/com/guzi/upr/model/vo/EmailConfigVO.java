package com.guzi.upr.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Data
public class EmailConfigVO {
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

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
