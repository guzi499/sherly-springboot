package com.guzi.sherly.modules.email.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@Data
@TableName("sys_email_config")
@EqualsAndHashCode(callSuper = true)
public class EmailConfigDO extends BaseModel {
    /** 编号 */
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
