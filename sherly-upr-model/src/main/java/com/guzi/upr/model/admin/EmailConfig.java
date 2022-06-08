package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.upr.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Data
@TableName("sys_email_config")
public class EmailConfig extends BaseModel {
    @ApiModelProperty("id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("邮件服务器SMTP地址")
    private String host;

    @ApiModelProperty("邮件服务器SMTP端口")
    private String port;

    @ApiModelProperty("发件者用户名")
    private String user;

    @ApiModelProperty("密码")
    private String pass;

    @ApiModelProperty("收件人")
    private String fromUser;
}
