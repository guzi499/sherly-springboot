package com.guzi.upr.log.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.upr.model.BaseModel;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Data
@TableName("sys_operation_log")
public class OperationLog extends BaseModel {

    /** 日志id */
    @TableId(type = IdType.AUTO)
    private Long logId;

    /** 类型 */
    private String type;

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

    /** 请求浏览器 */
    private String browser;

    /** 耗时 */
    private Long duration;

    /** 异常描述 */
    private String exception;
}
