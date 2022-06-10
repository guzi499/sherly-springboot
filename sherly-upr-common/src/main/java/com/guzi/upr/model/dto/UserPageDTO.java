package com.guzi.upr.model.dto;

import com.guzi.upr.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 付东辉
 * @date 2022/4/9
 */
@Data
public class UserPageDTO extends PageQuery {
    /** 昵称 */
    @ApiModelProperty("昵称")
    private String nickname;

    /** 姓名 */
    @ApiModelProperty("姓名")
    private String realName;

    /** 手机号 */
    @ApiModelProperty("手机号")
    private String phone;

    /** 用户邮箱 */
    @ApiModelProperty("用户邮箱")
    private String email;

    /** 部门id */
    @ApiModelProperty("部门id")
    private Long departmentId;

    /** 0不可用 1可用 */
    @ApiModelProperty("0不可用 1可用")
    private Integer enable;

    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
