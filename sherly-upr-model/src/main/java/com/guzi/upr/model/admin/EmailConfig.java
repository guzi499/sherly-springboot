package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.upr.model.BaseModel;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Data
@TableName("sys_email_config")
public class EmailConfig extends BaseModel {
    /** id */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 邮件服务器SMTP地址 */
    private String host;

    /** 邮件服务器SMTP端口 */
    private String port;

    /** 发件人名称 */
    private String senderUser;

    /** 密码 */
    private String password;

    /** 发件人邮箱 */
    private String senderEmail;
}
