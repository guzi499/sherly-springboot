package com.guzi.upr.log.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.guzi.upr.model.BaseModel;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
public class LoginLog extends BaseModel {
    /** 日志id */
    @TableId(type = IdType.AUTO)
    private Long logId;

    /** 描述 */
    private String description;

    /** 请求ip */
    private String ip;

    /** 请求地址 */
    private String address;

    /** 请求浏览器 */
    private String browser;

    /** 耗时 */
    private Long duration;

    /** 登录结果 */
    private Integer result;
}
