package com.guzi.sherly.admin.user.dto;

import cn.hutool.core.date.DatePattern;
import com.guzi.sherly.common.enums.UsableEnum;
import com.guzi.sherly.common.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author 付东辉
 * @date 2022/4/9
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageDTO extends PageQuery {
    /** 昵称 */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String realName;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /** 用户邮箱 */
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    /** 部门ids */
    @ApiModelProperty(value = "部门ids")
    private List<Long> departmentIds;

    /** 可用性 */
    @ApiModelProperty(value = "可用性")
    private UsableEnum enable;

    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date beginTime;

    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date endTime;
}
