package com.guzi.sherly.modules.log.vo;

import com.guzi.sherly.modules.log.enums.LoginResultEnum;
import com.guzi.sherly.modules.log.enums.LoginTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/7/15
 */
@Data
public class LoginLogPageVO {
    /** 日志编号 */
    @ApiModelProperty(value = "日志编号")
    private Long logId;

    /** 登录账号 */
    @ApiModelProperty(value = "登录账号")
    private String username;

    /** 登录方式 */
    @ApiModelProperty(value = "登录方式")
    private LoginTypeEnum type;

    /** 请求ip */
    @ApiModelProperty(value = "请求ip")
    private String ip;

    /** 请求地址 */
    @ApiModelProperty(value = "请求地址")
    private String address;

    /** 请求系统 */
    @ApiModelProperty(value = "请求系统")
    private String os;

    /** 请求浏览器 */
    @ApiModelProperty(value = "请求浏览器")
    private String browser;

    /** 登录结果 */
    @ApiModelProperty(value = "登录结果")
    private LoginResultEnum result;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
