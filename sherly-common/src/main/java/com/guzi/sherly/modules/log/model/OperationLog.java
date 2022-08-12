package com.guzi.sherly.modules.log.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Data
@TableName("sys_operation_log")
public class OperationLog {

    /** 日志id */
    @TableId(type = IdType.AUTO)
    private Long logId;

    /** 日志类型[enum] */
    private Integer type;

    /** 描述 */
    private String description;

    /** 请求方式 */
    private String requestMethod;

    /** 请求uri */
    private String uri;

    /** 请求参数 */
    private String requestParams;

    /** 请求ip */
    private String ip;

    /** 请求地址 */
    private String address;

    /** 请求系统 */
    private String os;

    /** 请求浏览器 */
    private String browser;

    /** 耗时 */
    private Long duration;

    /** 异常描述 */
    private String exception;

    /** 创建时间 */
    private Date createTime;

    /** 创建人id */
    private Long createUserId;
}
