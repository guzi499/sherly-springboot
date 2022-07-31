package com.guzi.upr.model.dto;

import cn.hutool.core.date.DatePattern;
import com.guzi.upr.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/7/15
 */
@Data
public class LoginLogPageDTO extends PageQuery {

    /** 登录账号 */
    @ApiModelProperty(value = "登录账号")
    private String username;

    /** 登录方式[enum] */
    @ApiModelProperty(value = "登录方式[enum]")
    private Integer type;

    /** 登录结果[enum] */
    @ApiModelProperty(value = "登录结果[enum]")
    private Integer result;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date beginTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date endTime;
}
